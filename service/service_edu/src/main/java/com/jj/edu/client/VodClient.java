package com.jj.edu.client;

import com.jj.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) // nacos中微服务名称
public interface VodClient {
    // 根据视频id删除视频
    @DeleteMapping("/eduvod/video/deleteAlyVideo/{id}")
    R deleteAlyVideo(@PathVariable("id") String id);
    // 根据多个视频id删除视频
    // 根据多个视频id 删除视频
    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteAlyVodBatch(@RequestParam("videoIds") List<String> videoIds);
}
