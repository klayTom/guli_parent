package com.jj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.edu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-08-05
 */
public interface CommentService extends IService<EduComment> {

    // 根据课程id得到分页评论
    Map<String, Object> getCommentList(Page<EduComment> page, String courseId);
}
