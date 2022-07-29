package com.jj.oss.service.impl;

import com.google.gson.Gson;
import com.jj.commonutils.R;
import com.jj.oss.service.OssService;
import com.jj.oss.utils.ConstantPropertiesUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    public String upload(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = ConstantPropertiesUtils.ACCESSKEY;
        String secretKey = ConstantPropertiesUtils.SECRETKEY;
        String bucket = ConstantPropertiesUtils.BUCKET;
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = file.getOriginalFilename();
        // 1. 在文件名里添加随机唯一的值
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        key = uuid + key;
        // 2. 把文件按照日期分类
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        key = dateTime + "/" + key;
        try {
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
            String url = ConstantPropertiesUtils.DOMAIN + "/" + key;
//
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
