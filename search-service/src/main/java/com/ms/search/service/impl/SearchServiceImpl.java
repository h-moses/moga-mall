package com.ms.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ms.common.constant.IndexConstant;
import com.ms.common.enums.BizExceptionCode;
import com.ms.common.exception.BizException;
import com.ms.common.to.es.SkuEsModel;
import com.ms.search.domain.vo.SearchParamVo;
import com.ms.search.domain.vo.SearchResVo;
import com.ms.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    RestHighLevelClient elasticsearchClient;


    @Override
    public SearchResVo search(SearchParamVo searchParamVo) {
        SearchRequest searchRequest = buildSearchQuery(searchParamVo);
        try {
            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            return buildSearchResult(searchResponse, searchParamVo.getPageNum());
        } catch (IOException e) {
            throw new BizException(BizExceptionCode.ES_ERROR);
        }
    }

    private SearchRequest buildSearchQuery(SearchParamVo searchParamVo) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolBuilder = org.elasticsearch.index.query.QueryBuilders.boolQuery();

        // 关键字查询
        if (StringUtils.hasText(searchParamVo.getKeyword())) {
            boolBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParamVo.getKeyword()));
        }
        // 按照三级分类查询
        if (null != searchParamVo.getThirdCategoryId()) {
            boolBuilder.filter(QueryBuilders.termQuery("categoryId", searchParamVo.getThirdCategoryId()));
        }
        // 按照品牌id查询
        if (!searchParamVo.getBrandId().isEmpty()) {
            boolBuilder.filter(QueryBuilders.termsQuery("brandId", searchParamVo.getBrandId()));
        }
        // 是否存在库存查询
        if (null != searchParamVo.getHasStock()) {
            boolBuilder.filter(QueryBuilders.termQuery("hasStock", 1 == searchParamVo.getHasStock()));
        }
        // 价格区间
        if (StringUtils.hasText(searchParamVo.getSkuPrice())) {
            RangeQueryBuilder rangedQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] strings = searchParamVo.getSkuPrice().split("_");
            if (2 == strings.length) {
                rangedQuery.gte(strings[0]).lte(strings[1]);
            } else if (strings.length == 1) {
                if (searchParamVo.getSkuPrice().startsWith("_")) {
                    rangedQuery.lte(strings[0]);
                }
                if (searchParamVo.getSkuPrice().endsWith("_")) {
                    rangedQuery.gte(strings[0]);
                }
            }
            boolBuilder.filter(rangedQuery);
        }
        // 属性查询
        if (!searchParamVo.getAttrs().isEmpty()) {
            List<String> attrs = searchParamVo.getAttrs();
            for (String attr: attrs) {
                BoolQueryBuilder attrBoolQuery = QueryBuilders.boolQuery();
                // 以_分割，属性id和值
                String[] kv = attr.split("_");
                String k = kv[0];
                // 得到属性的值
                String[] val = kv[1].split(":");
                attrBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", k));
                attrBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", val));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", attrBoolQuery, ScoreMode.None);
                boolBuilder.filter(nestedQuery);
            }
        }

        searchSourceBuilder.query(boolBuilder);

        // 排序、分页、高亮
        if (StringUtils.hasText(searchParamVo.getSort())) {
            String sort = searchParamVo.getSort();
            String[] so = sort.split("_");
            SortOrder order = so[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(so[0], order);
        }

        searchSourceBuilder.from((searchParamVo.getPageNum() - 1) * IndexConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(IndexConstant.PRODUCT_PAGESIZE);

        if (StringUtils.hasText(searchParamVo.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        // 品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
        brandAgg.field("brandId").size(50);

        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(10));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(10));

        searchSourceBuilder.aggregation(brandAgg);

        // 分类聚合
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category_agg");
        categoryAgg.field("categoryId").size(20);

        categoryAgg.subAggregation(AggregationBuilders.terms("category_name_agg").field("categoryName").size(1));

        searchSourceBuilder.aggregation(categoryAgg);

        // 属性聚合
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");

        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(10));
        attrAgg.subAggregation(attrIdAgg);

        searchSourceBuilder.aggregation(attrAgg);

        String string = searchSourceBuilder.toString();

        log.info("[SearchServiceImpl]: 构建的DSL语句：" + string);

        return new SearchRequest(new String[]{IndexConstant.PRODUCT}, searchSourceBuilder);
    }

    private SearchResVo buildSearchResult(SearchResponse searchResponse, Integer pageNum) {
        SearchResVo searchResVo = new SearchResVo();

        SearchHits hits = searchResponse.getHits();
        // 商品信息
        List<SkuEsModel> models = new ArrayList<>();
        if (null != hits.getHits() && hits.getHits().length > 0) {
            for (SearchHit searchHit: hits.getHits()) {
                String sourceAsString = searchHit.getSourceAsString();
                SkuEsModel skuEsModel = JSONObject.parseObject(sourceAsString, SkuEsModel.class);
                HighlightField skuTitle = searchHit.getHighlightFields().get("skuTitle");
                String string = skuTitle.getFragments()[0].string();
                skuEsModel.setSkuTitle(string);
                models.add(skuEsModel);
            }
            searchResVo.setProducts(models);
        }
        // 属性信息
        ParsedNested attrAgg = searchResponse.getAggregations().get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        List<? extends Terms.Bucket> attrIdAggBuckets = attrIdAgg.getBuckets();
        List<SearchResVo.Attribute> attributeList = new ArrayList<>();
        for (Terms.Bucket attrAggBucket : attrIdAggBuckets) {
            long id = attrAggBucket.getKeyAsNumber().longValue();
            ParsedStringTerms attrNameAgg = attrAggBucket.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            ParsedStringTerms attrValueAgg = attrAggBucket.getAggregations().get("attr_value_agg");
            List<? extends Terms.Bucket> attrValueAggBuckets = attrValueAgg.getBuckets();
            List<String> attrValueList = attrValueAggBuckets.stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
            SearchResVo.Attribute attribute = SearchResVo.Attribute.builder().id(id).attrName(attrName).attrValues(attrValueList).build();
            attributeList.add(attribute);
        }
        searchResVo.setAttributes(attributeList);
        // 品牌信息
        ParsedLongTerms brandAgg = searchResponse.getAggregations().get("brand_agg");
        List<? extends Terms.Bucket> brandAggBuckets = brandAgg.getBuckets();
        List<SearchResVo.Brand> brandList = new ArrayList<>();
        for (Terms.Bucket brandAggBucket : brandAggBuckets) {
            // 品牌id
            long brandId = brandAggBucket.getKeyAsNumber().longValue();
            // 品牌名字
            ParsedStringTerms brandNameAgg = brandAggBucket.getAggregations().get("brand_name_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            // 品牌图片
            ParsedStringTerms brandImgAgg = brandAggBucket.getAggregations().get("brand_img_agg");
            String brandImg = Optional.ofNullable(brandImgAgg.getBuckets())
                    .map(buckets -> buckets.isEmpty() ? null : buckets.get(0))
                    .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                    .orElse("");

            SearchResVo.Brand brand = SearchResVo.Brand.builder().id(brandId).brandName(brandName).brandPic(brandImg).build();
            brandList.add(brand);
        }
        searchResVo.setBrands(brandList);
        // 分类信息
        ParsedLongTerms categoryAgg = searchResponse.getAggregations().get("category_agg");
        List<? extends Terms.Bucket> buckets = categoryAgg.getBuckets();
        List<SearchResVo.Category> categoryList = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            ParsedStringTerms categoryNameAgg = bucket.getAggregations().get("category_name_agg");
            String categoryName = categoryNameAgg.getBuckets().get(0).getKeyAsString();
            SearchResVo.Category category = SearchResVo.Category.builder().id((Long) bucket.getKeyAsNumber()).categoryName(categoryName).build();
            categoryList.add(category);
        }
        searchResVo.setCategories(categoryList);
        // 分页信息
        searchResVo.setPageNum(pageNum);
        searchResVo.setTotal(hits.getTotalHits().value);
        long totalPage = 0 == searchResVo.getTotal() % IndexConstant.PRODUCT_PAGESIZE ? searchResVo.getTotal() / IndexConstant.PRODUCT_PAGESIZE : searchResVo.getTotal() / IndexConstant.PRODUCT_PAGESIZE + 1;
        searchResVo.setTotalPage((int)totalPage);
        return searchResVo;
    }
}
