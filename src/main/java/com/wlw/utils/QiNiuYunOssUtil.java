package com.wlw.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.google.gson.Gson;

import java.io.InputStream;

/**
 * @author zsw
 */
public class QiNiuYunOssUtil {

    private static String accesskey = "acoK0xTB5FX1bKGpZWmjptIu3yGxfckytFPudPLV";
    private static String secretkey = "oQUhdGtof3uuWBlbkNhD8k1eIJDfAxNC14GIgibV";
    private static String bucket = "envet-article";

    public static  String uploadFile(String objectName, InputStream inputStream) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = objectName;
        String url = "";
        try {
            Auth auth = Auth.create(accesskey, secretkey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                url = "http://sro29nc2h.hn-bkt.clouddn.com/" + key;
                return url;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "上传失败";
    }
}
