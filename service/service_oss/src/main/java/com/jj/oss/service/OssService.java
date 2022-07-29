package com.jj.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upload(MultipartFile multipartFile);
}
