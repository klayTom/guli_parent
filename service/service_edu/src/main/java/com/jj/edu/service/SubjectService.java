package com.jj.edu.service;

import com.jj.edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-07-25
 */
public interface SubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    // 课程分类展示(树形)
    List<OneSubject> getAllOneTwoSubject();
}
