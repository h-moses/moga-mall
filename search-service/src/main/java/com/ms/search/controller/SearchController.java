package com.ms.search.controller;

import com.ms.common.api.Response;
import com.ms.search.domain.vo.SearchParamVo;
import com.ms.search.domain.vo.SearchResVo;
import com.ms.search.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/search")
@RestController
public class SearchController {


    @Resource
    SearchService searchService;

    @PostMapping("/page")
    public Response<SearchResVo> search(SearchParamVo searchParamVo) {

        SearchResVo searchResVo = searchService.search(searchParamVo);
        return Response.SUCCESS(searchResVo);
    }

}
