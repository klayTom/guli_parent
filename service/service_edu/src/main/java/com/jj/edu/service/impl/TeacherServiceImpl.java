package com.jj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.edu.entity.EduTeacher;
import com.jj.edu.mapper.TeacherMapper;
import com.jj.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-07-29
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, EduTeacher> implements TeacherService {

    // 添加redis缓存
    @Cacheable(value = "teacher", key = "'selectIndexList'")
    @Override
    public List<EduTeacher> getIndexTeacher() {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(eduTeacherQueryWrapper);
        return teacherList;
    }

    @Override
    public Map<String, Object> getTeacherPage(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(teacherPage,wrapper);

        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long pages = teacherPage.getPages();
        long size = teacherPage.getSize();
        long total = teacherPage.getTotal();
        boolean hasNext = teacherPage.hasNext();//下一页
        boolean hasPrevious = teacherPage.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String,Object> map = new HashMap<>();
        map.put("items",records);
        map.put("pages",pages);
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }


}
