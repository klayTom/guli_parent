package com.jj.edu.service;

import com.jj.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.edu.entity.vo.CourseInfoVo;
import com.jj.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-07-26
 */
public interface CourseService extends IService<EduCourse> {

    // 新增课程信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程信息
    CourseInfoVo getCourseInfo(String id);

    // 修改课程基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 得到确认发布课程信息
    CoursePublishVo getCoursePublishVo(String id);
}
