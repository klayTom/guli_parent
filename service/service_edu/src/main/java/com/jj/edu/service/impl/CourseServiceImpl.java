package com.jj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.edu.entity.EduCourse;
import com.jj.edu.entity.EduCourseDescription;
import com.jj.edu.entity.frontvo.CourseQueryVo;
import com.jj.edu.entity.frontvo.CourseWebVo;
import com.jj.edu.entity.vo.CourseInfoVo;
import com.jj.edu.entity.vo.CoursePublishVo;
import com.jj.edu.mapper.CourseMapper;
import com.jj.edu.service.ChapterService;
import com.jj.edu.service.CourseDescriptionService;
import com.jj.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.edu.service.VideoService;
import com.jj.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, EduCourse> implements CourseService {

    //课程描述注入
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表添加课程基本信息
        EduCourse eduCourse =new EduCourse();
        //将courseInfoVo对象转化成eduCourse
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            throw new GuliException(20001,"添加课程信息失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        //2 向课程简介表添加课程简介
        EduCourseDescription courseDescription =new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id 就是课程id，记得将实体类中的填充模式改为INPUT
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    // 根据课程id查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String id) {
        // 1. 查询课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        // 2. 查询课程描述表
        EduCourseDescription description = courseDescriptionService.getById(id);
        BeanUtils.copyProperties(description,courseInfoVo);

        return courseInfoVo;
    }

    // 修改课程基本信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result == 0) {
            throw new GuliException(20001, "修改课程失败");
        }
        // 2. 修改课程简介表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVo(id);
        return coursePublishVo;
    }

    @Override
    public void deleteCourse(String id) {
        // 1. 根据课程id删除小节
        videoService.deleteByCourseId(id);
        // 2. 根据课程id删除章节
        chapterService.deleteByCourseId(id);
        // 3. 根据课程id删除描述
        courseDescriptionService.removeById(id);
        // 4. 删除课程
        int result = baseMapper.deleteById(id);
        if (result == 0) {
            throw new GuliException(2001, "删除失败");
        }
    }

    // 添加redis缓存
    @Cacheable(value = "course", key = "'selectIndexList'")
    @Override
    public List<EduCourse> getIndexCourse() {
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(eduCourseQueryWrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //判断条件值是否为空，不为空拼接
        if(null != courseQueryVo && !StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if(null != courseQueryVo && !StringUtils.isEmpty(courseQueryVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if(null != courseQueryVo && !StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (null != courseQueryVo && !StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }
        if (null != courseQueryVo && !StringUtils.isEmpty(courseQueryVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        // 查询已发布的课程
        wrapper.eq("status", "Normal");

        baseMapper.selectPage(page,wrapper);

        List<EduCourse> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();//下一页
        boolean hasPrevious = page.hasPrevious();//上一页

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

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getBaseCourseInfo(courseId);
        return courseWebVo;
    }
}
