package com.ms.product.service;

import java.io.InputStream;
import java.util.Optional;

public interface IMinioService {

    void uploadObject(String objectName, InputStream inputStream, long size, String contentType) throws Exception;

    Optional<String> downloadObject(String objectName) throws Exception;

    void deleteObject(String objectName) throws Exception;
}
