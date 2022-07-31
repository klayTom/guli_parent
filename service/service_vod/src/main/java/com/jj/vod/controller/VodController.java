package com.jj.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.jj.commonutils.R;
import com.jj.servicebase.exceptionhandler.GuliException;
import com.jj.vod.service.VodService;
import com.jj.vod.utils.ConstantVodUtils;
import com.jj.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 根据视频id删除视频
    @DeleteMapping("deleteAlyVideo/{id}")
    public R deleteAlyVideo(@PathVariable String id) {
        vodService.deleteVodById(id);
        return R.ok();
    }

    // 根据多个视频id 删除视频
    @DeleteMapping("delete-batch")
    public R deleteAlyVodBatch(@RequestParam("videoIds") List<String> videoIds) {
        vodService.deleteVodBatch(videoIds);
        return R.ok();
    }
}
