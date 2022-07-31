package com.jj.edu.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.edu.client.VodClient;
import com.jj.edu.entity.EduVideo;
import com.jj.edu.mapper.VideoMapper;
import com.jj.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, EduVideo> implements VideoService {

    @Autowired
    private VodClient vodClient;


    @Override
    public void deleteByCourseId(String id) {
        // 根据课程id 查询出所有的视频id
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",id);
        eduVideoQueryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(eduVideoQueryWrapper);

        // 通过stream得到所有视频id
        List<String> idList = videoList.stream().filter(eduVideo -> !StringUtils.isEmpty(eduVideo.getVideoSourceId()))
                .map(EduVideo::getVideoSourceId)
                .collect(Collectors.toList());
        vodClient.deleteAlyVodBatch(idList);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
