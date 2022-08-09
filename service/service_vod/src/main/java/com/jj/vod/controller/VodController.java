package com.jj.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jj.commonutils.R;
import com.jj.servicebase.exceptionhandler.GuliException;
import com.jj.vod.service.VodService;
import com.jj.vod.utils.ConstantVodUtils;
import com.jj.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")

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

    // 根据视频id得到播放凭证
    @GetMapping("playAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            //创建获取视频地址request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //向request对象里面设置视频id
            request.setVideoId(vid);

            //调用初始化对象里面的方法，传递request，获取数据
            response = client.getAcsResponse(request);
            return R.ok().data("playAuth", response.getPlayAuth());
        }catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }

}
