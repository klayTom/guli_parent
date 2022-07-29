package com.jj.edu.service.impl;

import com.jj.edu.entity.EduCourseDescription;
import com.jj.edu.mapper.CourseDescriptionMapper;
import com.jj.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, EduCourseDescription> implements CourseDescriptionService {

}
