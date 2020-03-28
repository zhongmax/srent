package com.csmaxwell.srent.core.storage;


import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 对象存储接口
 */
public interface Storage {

    void store(InputStream inputStream, long contentLength,
               String contentType, String keyName);

    Stream<Path> loadALL();

    Path load(String keyName);

    Resource loadAsResource(String keyName);

    void delete(String keyName);

    String generateUrl(String keyName);

}
