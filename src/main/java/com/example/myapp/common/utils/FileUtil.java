package com.example.myapp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {

    /**
     * 在basePath下保存上传的文件夹
     *
     * @param basePath
     * @param files
     */
    public static void saveMultiFile(String basePath, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            log.warn("no files uploaded");
            return;
        } else if (files[0].getOriginalFilename() == null || files[0].getOriginalFilename().equals("")) {
            log.warn("Empty package: no files to be uploaded");
            return;
        }
        for (MultipartFile file : files) {
            String relativePath = file.getOriginalFilename();
            String filePath = basePath + relativePath;
            log.info("uploading " + relativePath + " to " + filePath + "......");
            makeDir(filePath);
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
                log.info("successful upload");
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 确保目录存在，不存在则创建
     *
     * @param filePath
     */
    private static void makeDir(String filePath) {
        if (filePath.lastIndexOf('/') > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }
}

