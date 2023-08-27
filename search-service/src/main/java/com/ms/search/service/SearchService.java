package com.ms.search.service;

import com.ms.search.domain.vo.SearchParamVo;
import com.ms.search.domain.vo.SearchResVo;

public interface SearchService {

    SearchResVo search(SearchParamVo searchParamVo);
}
