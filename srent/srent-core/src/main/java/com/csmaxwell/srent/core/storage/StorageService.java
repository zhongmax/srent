package com.csmaxwell.srent.core.storage;

import com.csmaxwell.srent.core.util.CharUtil;
import com.csmaxwell.srent.db.domain.SrentStorage;
import com.csmaxwell.srent.db.service.SrentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 提供存储服务类，所有存储服务均由该类对外提供
 */
public class StorageService {

    private String active;
    private Storage storage;

    @Autowired
    private SrentStorageService srentStorageService;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件索引名
     * @return
     */
    public SrentStorage store(InputStream inputStream, long contentLength, String contentType, String fileName) {
        String key = generateKey(fileName);
        //System.out.println("2、store对象");
        storage.store(inputStream, contentLength, contentType, key);

        String url = generateUrl(key);
        //System.out.println("3、输出url" + url);
        SrentStorage storageInfo = new SrentStorage();
        storageInfo.setName(fileName);
        storageInfo.setSize((int) contentLength);
        storageInfo.setType(contentType);
        storageInfo.setKey(key);
        storageInfo.setUrl(url);
        srentStorageService.add(storageInfo);

        return storageInfo;
    }

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        SrentStorage storageInfo = null;

        do {
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = srentStorageService.findByKey(key);
        } while (storageInfo != null);

        return key;
    }

    private String generateUrl(String keyName) {
        return storage.generateUrl(keyName);
    }

    public Stream<Path> loadAll() {
        return storage.loadALL();
    }

    public Path load(String keyName) {
        return storage.load(keyName);
    }

    public Resource loadAsResource(String keyName) {
        return storage.loadAsResource(keyName);
    }

    public void delete(String keyName) {
        storage.delete(keyName);
    }
}
