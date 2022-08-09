package com.jj.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.edu.entity.frontvo.CourseQueryVo;
import com.jj.edu.entity.frontvo.CourseWebVo;
import com.jj.edu.entity.vo.CourseInfoVo;
import com.jj.edu.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

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

    void deleteCourse(String id);

    // 根据课程id排序 显示前8条课程
    List<EduCourse> getIndexCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    //1 根据课程id得到课程详细信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
