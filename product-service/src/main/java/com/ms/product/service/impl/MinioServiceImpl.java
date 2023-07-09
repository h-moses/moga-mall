package com.ms.product.service.impl;

import com.ms.common.enums.SysExceptionCode;
import com.ms.common.exception.SysException;
import com.ms.product.service.IMinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MinioServiceImpl implements IMinioService {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public void uploadObject(String objectName, InputStream inputStream, long size, String contentType) throws Exception {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
    }

    @Override
    public Optional<String> downloadObject(String objectName) throws Exception {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        InputStream stream = minioClient.getObject(args);
        // 不适用于读取大文件，大文件存储在内存，可能导致内存不足
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        byte[] bytes = buffer.toByteArray();
        return Optional.of(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public void deleteObject(String objectName) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        minioClient.removeObject(args);
    }

    @Override
    public String getFileUrl(String objectName) {
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(60 * 60 * 24)
                    .build());
        } catch (Exception e) {
            throw new SysException(SysExceptionCode.URL_NOT_EXIST);
        }
        return url;
    }
}
