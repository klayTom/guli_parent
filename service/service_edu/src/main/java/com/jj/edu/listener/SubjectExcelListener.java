package com.jj.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.edu.entity.EduSubject;
import com.jj.edu.entity.excel.SubjectData;
import com.jj.edu.service.SubjectService;
import com.jj.servicebase.exceptionhandler.GuliException;
import org.apache.poi.sl.draw.geom.Guide;

import javax.management.Query;
import javax.security.auth.Subject;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    // 因为SubjectExcelListener 不能交给spring管理 需要手动new 不能注入其他对象
    // 不能操作数据库
    public SubjectService subjectService;
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {

    }
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 判断excel 内容是否为空
        if (subjectData == null) {
            throw new GuliException(20001,"文件数据为空");
        }
        // 数据是一行一行的读 每次读取有两个值 第一个是一级目录 第二个是二级目录
        // 判断一级目录是否重复
        // 添加一级目录
        EduSubject existOneSubject = this.existOneSubjectName(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { // 没有相同目录 进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        // 获取一级分类的id值
        String pid = existOneSubject.getId();
        // 判断二级目录是否存在
        // 添加二级目录
        EduSubject existTwoSubject = this.existTwoSubjectName(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }

    }

    // 判断是否存在一级目录
    private EduSubject existOneSubjectName(SubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id","0");
        EduSubject onSubject = subjectService.getOne(wrapper);
        return onSubject;
    }

    // 判断是否存在二级目录
    private EduSubject existTwoSubjectName(SubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name)
                .eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
