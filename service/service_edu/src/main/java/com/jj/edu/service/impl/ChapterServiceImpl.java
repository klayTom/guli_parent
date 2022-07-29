package com.jj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.edu.entity.EduChapter;
import com.jj.edu.entity.EduVideo;
import com.jj.edu.entity.chapter.ChapterVo;
import com.jj.edu.entity.chapter.VideoVo;
import com.jj.edu.mapper.ChapterMapper;
import com.jj.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.edu.service.VideoService;
import com.jj.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-07-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, EduChapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {

        //1 根据课程id查询课程里面的章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterQueryWrapper);
        //2 根据课程id查询课程里面的小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = videoService.list(eduVideoQueryWrapper);

        // 最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        //3 遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //4 遍历查询小节list集合进行封装.
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            finalList.add(chapterVo);

        }

        return finalList;
    }

    // 根据id删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        // 1. 根据chapterId查询是否有小节 有小节不做删除
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(eduVideoQueryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }
}
