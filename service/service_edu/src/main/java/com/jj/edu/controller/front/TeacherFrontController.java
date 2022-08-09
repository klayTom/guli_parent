package com.jj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.R;
import com.jj.edu.entity.EduCourse;
import com.jj.edu.entity.EduTeacher;
import com.jj.edu.service.CourseService;
import com.jj.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/edu/teacherfront")
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    // 获取分页讲师
    @GetMapping("getTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        Map<String, Object> map = teacherService.getTeacherPage(teacherPage);
        return R.ok().data(map);
    }

    // 根据讲师id获取讲师详细信息和课程信息
    @GetMapping("getInfoAndCourse/{teacherId}")
    public R getInfoAndCourse(@PathVariable String teacherId) {
        //1 . 获取讲师详细信息
        EduTeacher teacher = teacherService.getById(teacherId);
        //2 . 获取课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacherInfo", teacher).data("courseList", courseList);
    }

}
