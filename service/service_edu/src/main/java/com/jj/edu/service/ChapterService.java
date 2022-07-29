package com.jj.edu.service;

import com.jj.edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
public interface ChapterService extends IService<EduChapter> {
    // 根据课程id得到 课程大纲
    List<ChapterVo> getChapterVideo(String courseId);

    // 根据id删除章节
    boolean deleteChapter(String chapterId);
}
