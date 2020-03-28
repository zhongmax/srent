package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.storage.StorageService;
import com.csmaxwell.srent.core.util.CharUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentStorage;
import com.csmaxwell.srent.db.service.SrentStorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 对象存储服务
 */
@RestController
@RequestMapping("/wx/storage")
@Validated
public class WxStorageController {
    private final Log logger = LogFactory.getLog(WxStorageController.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private SrentStorageService srentStorageService;

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

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("1、上传图片");
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        SrentStorage srentStorage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        System.out.println("图片结束");
        return ResponseUtil.ok(srentStorage);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        SrentStorage srentStorage = srentStorageService.findByKey(key);

        if (key == null) {
            return ResponseEntity.notFound().build();
        }

        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }

        String type = srentStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(mediaType).body(file);
    }
}
