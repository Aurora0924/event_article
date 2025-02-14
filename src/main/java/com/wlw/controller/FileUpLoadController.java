package com.wlw.controller;

import com.wlw.pojo.Result;
import com.wlw.utils.QiNiuYunOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author zsw
 */
@RestController
public class FileUpLoadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //将文件保存到指定位置,为图片名称增加uuid
        String fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = QiNiuYunOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
