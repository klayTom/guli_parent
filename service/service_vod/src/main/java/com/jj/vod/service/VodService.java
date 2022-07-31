package com.jj.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 上传到阿里视频
    String uploadVideo(MultipartFile file);

    // 根据多个视频id 删除视频
    void deleteVodBatch(List<String> videoIds);

    // 根据视频id删除视频
    void deleteVodById(String id);
}
