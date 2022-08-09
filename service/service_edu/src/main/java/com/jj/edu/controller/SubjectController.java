package com.jj.edu.controller;


import com.jj.commonutils.R;
import com.jj.edu.entity.subject.OneSubject;
import com.jj.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/edu/subject")

public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    // 添加课程分类
    // 获取上传过来的文件 把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        // 得到上传的excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    // 课程分类展示(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}


