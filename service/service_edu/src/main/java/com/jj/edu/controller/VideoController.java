package com.jj.edu.controller;


import com.jj.commonutils.R;
import com.jj.edu.client.VodClient;
import com.jj.edu.entity.EduVideo;
import com.jj.edu.service.VideoService;
import com.jj.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    // 新增小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        // 根据videoId 查询 videoSourceId
        EduVideo video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();


        // 判断小节中是否有视频
        if (!StringUtils.isBlank(videoSourceId)) {
            R reslut = vodClient.deleteAlyVideo(videoSourceId);
            if (reslut.getCode() == 20001){
                throw new GuliException(20001, "删除视频失败，熔断器...");
            }
        }
        // 通过视频id 远程调用 service-vod 服务中的方法删除视频
        videoService.removeById(videoId);
        return R.ok();
    }

    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    // 修改小节信息
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

