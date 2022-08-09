package com.jj.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.JwtUtils;
import com.jj.commonutils.R;
import com.jj.commonutils.UcenterVo;
import com.jj.edu.client.UcenterClient;
import com.jj.edu.entity.EduComment;
import com.jj.edu.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-05
 */
@RestController
@RequestMapping("/edu/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UcenterClient ucenterClient;


    // 根据课程id得到分页评论
    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("getPageComment/{current}/{limit}")
    public R getPageComment(@PathVariable Long current, @PathVariable Long limit, String courseId) {
        Page<EduComment> page = new Page<>(current, limit);
        Map<String,Object> map = commentService.getCommentList(page,courseId);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

       // 远程调用service-ucenter
        UcenterVo ucenterVo = ucenterClient.getInfoById(memberId);
        comment.setNickname(ucenterVo.getNickname());
        comment.setAvatar(ucenterVo.getAvatar());

        commentService.save(comment);
        return R.ok();
    }




}

