package com.jj.edu.mapper;

import com.jj.edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jj.edu.entity.frontvo.CourseWebVo;
import com.jj.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
public interface CourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
