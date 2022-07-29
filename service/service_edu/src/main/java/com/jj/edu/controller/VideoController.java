package com.jj.edu.controller;


import com.jj.commonutils.R;
import com.jj.edu.entity.EduVideo;
import com.jj.edu.service.VideoService;
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

    // 新增小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
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

