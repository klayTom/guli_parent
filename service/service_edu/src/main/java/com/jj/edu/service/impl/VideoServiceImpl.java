package com.jj.edu.service.impl;

import com.jj.edu.entity.EduVideo;
import com.jj.edu.mapper.VideoMapper;
import com.jj.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
