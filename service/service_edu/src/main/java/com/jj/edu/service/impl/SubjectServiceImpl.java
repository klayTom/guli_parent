package com.jj.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.jj.edu.entity.EduSubject;
import com.jj.edu.entity.excel.SubjectData;
import com.jj.edu.entity.subject.OneSubject;
import com.jj.edu.entity.subject.TwoSubject;
import com.jj.edu.listener.SubjectExcelListener;
import com.jj.edu.mapper.SubjectMapper;
import com.jj.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-07-25
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, EduSubject> implements SubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            // 得到输入流
            InputStream inputStream = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 课程分类展示(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 得到一级目录
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);

        // 得到二级目录
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);
        // 封装一级目录

        // 最终数据格式
        List<OneSubject> finalOneSubjectList = new ArrayList<>();

        for (EduSubject subject1 : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            // 封装一级目录
            BeanUtils.copyProperties(subject1,oneSubject);

            //封装二级目录
            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            // 遍历查找一级目录对应的二级目录
            for (EduSubject subject2 : twoSubjects) {
                TwoSubject twoSubject = new TwoSubject();
                // 判断二级目录对应的parent_id是否等于一级目录id
                if (subject1.getId().equals(subject2.getParentId())) {
                    BeanUtils.copyProperties(subject2,twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoSubjectList);

            // 最终数据格式
            finalOneSubjectList.add(oneSubject);

        }
        // 封装二级目录
        return finalOneSubjectList;
    }
}
