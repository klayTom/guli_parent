package com.jj.edu.controller;


import com.jj.commonutils.R;
import com.jj.edu.entity.vo.CourseInfoVo;
import com.jj.edu.entity.vo.CoursePublishVo;
import com.jj.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    // 添加课程基本信息
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourse")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后课程id，为了后面添加大纲使用
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }

    // 根据课程id查询课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfoById(@PathVariable String id) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(id);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程基本信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 得到确认发布课程信息
    @GetMapping("getCoursePublishVo/{id}")
    public R getCoursePublishVo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }
}



