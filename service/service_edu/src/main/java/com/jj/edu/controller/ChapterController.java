package com.jj.edu.controller;


import com.jj.commonutils.R;
import com.jj.edu.entity.EduChapter;
import com.jj.edu.entity.chapter.ChapterVo;
import com.jj.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    // 根据课程id得到 课程大纲
    @GetMapping("getAllChapter/{courseId}")
    public R getAllChapter(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideo(courseId);
        return R.ok().data("chapterVideo",list);
    }

    // 添加课程章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    // 根据id 得到章节
    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId) {
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    // 根据id删除章节
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag == true) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}



