package com.jj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
public interface TeacherService extends IService<EduTeacher> {

    // 根据讲师id排序 得到前4条数据
    List<EduTeacher> getIndexTeacher();

    // 分页查询讲师
    Map<String,Object> getTeacherPage(Page<EduTeacher> teacherPage);


}
