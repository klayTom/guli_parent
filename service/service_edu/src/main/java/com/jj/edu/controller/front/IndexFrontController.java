package com.jj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.commonutils.R;
import com.jj.edu.entity.EduCourse;
import com.jj.edu.entity.EduTeacher;
import com.jj.edu.service.CourseService;
import com.jj.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/indexfront")

public class IndexFrontController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    @GetMapping("index")
    public R index () {
        // 根据课程id排序 显示前8条课程
        List<EduCourse> eduCourseList = courseService.getIndexCourse();


        // 根据讲师id排序 得到前4条数据
        List<EduTeacher> eduTeacherList = teacherService.getIndexTeacher();

        return R.ok().data("courseList",eduCourseList).data("teacherList",eduTeacherList);
    }
}