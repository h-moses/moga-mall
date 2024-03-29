package com.ms.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.ms.common.constant.IndexConstant;
import com.ms.common.to.SkuEsModel;
import com.ms.search.service.ProductSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSearchServiceImpl implements ProductSearchService {

    @Resource
    ElasticsearchClient elasticsearchClient;

    @Override
    public boolean onShelf(List<SkuEsModel> models) throws IOException {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        // TODO 建立attrs的nested
        for (SkuEsModel model: models) {
            builder.operations(op -> op.index(idx -> idx.index(IndexConstant.PRODUCT).id(model.getSkuId().toString()).document(model)));
        }
        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
        if (bulkResponse.errors()) {
            List<String> ids = bulkResponse.items().stream().map(BulkResponseItem::id).collect(Collectors.toList());
            log.error("[ProductSearchServiceImpl]: 商品上架错误：{}", ids);
        }
        return bulkResponse.errors();
    }
}
