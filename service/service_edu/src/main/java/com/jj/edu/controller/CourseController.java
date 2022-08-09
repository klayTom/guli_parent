package com.jj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.R;
import com.jj.edu.entity.EduCourse;
import com.jj.edu.entity.vo.CourseInfoVo;
import com.jj.edu.entity.vo.CoursePublishVo;
import com.jj.edu.entity.vo.CourseQuery;
import com.jj.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edu/course")
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

    // 条件查询课程并分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable Long current,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        // 按照时间降序排序
        wrapper.orderByDesc("gmt_create");
        courseService.page(pageCourse, wrapper);


        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        return R.ok().data("total", total).data("list",records);

    }

    // 删除课程
    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return R.ok();
    }

    @ApiOperation(value = "课程最终发布修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }
}



