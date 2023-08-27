package com.ms.search.service;

import com.ms.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSearchService {
    boolean onShelf(List<SkuEsModel> models) throws IOException;
}
