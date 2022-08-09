package com.jj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.R;
import com.jj.edu.entity.EduTeacher;
import com.jj.edu.entity.vo.TeacherQuery;
import com.jj.edu.service.impl.TeacherServiceImpl;
import com.jj.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-07-18
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
 // 解决跨域问题
public class TeacherController {
    @Autowired
    private TeacherServiceImpl teacherService;

    //    查询所有讲师方法
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> eduTeachers = teacherService.list(null);
        return R.ok().data("items", eduTeachers);
    }

    //  逻辑删除讲师方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable("id") String id) {
        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    // 分页查询讲师
    // current 代表当前页
    // limit 每页显示多少条数据

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") Long current, @PathVariable("limit") Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        // 得到总记录数
        long total = pageTeacher.getTotal();
        // 得到数据
        List<EduTeacher> records = pageTeacher.getRecords();
        // 返回map集合
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    // 多组条件查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Long current, @PathVariable("limit") Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 判断条件是否为空
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        // 按照时间降序排序
        wrapper.orderByDesc("gmt_create");

        // 调用此方法进行条件分页查询
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    // 新增讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 根据id获取讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {
        boolean result = teacherService.updateById(eduTeacher);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

