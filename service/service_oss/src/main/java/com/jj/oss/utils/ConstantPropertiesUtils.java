package com.jj.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${qiniu.kodo.accessKey}")
    private String accessKey;
    @Value("${qiniu.kodo.secretKey}")
    private String secretKey;
    @Value("${qiniu.kodo.domain}")
    private String domain;
    @Value("${qiniu.kodo.bucket}")
    private String bucket;


    public static String ACCESSKEY;
    public static String SECRETKEY;
    public static String DOMAIN;
    public static String BUCKET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEY = accessKey;
        SECRETKEY = secretKey;
        DOMAIN = domain;
        BUCKET = bucket;
    }
}
