package com.jj.edu.controller.front;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.JwtUtils;
import com.jj.commonutils.R;
import com.jj.commonutils.ordervo.OrderCourseVo;
import com.jj.edu.client.OrderCourseClient;
import com.jj.edu.entity.EduCourse;
import com.jj.edu.entity.chapter.ChapterVo;
import com.jj.edu.entity.frontvo.CourseQueryVo;
import com.jj.edu.entity.frontvo.CourseWebVo;
import com.jj.edu.service.ChapterService;
import com.jj.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/edu/coursefront")
public class CourseFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderCourseClient orderCourseClient;

    // 分页 条件查询课程信息
    @ApiOperation(value = "条件查询带分页的课程")
    @PostMapping("getFrontCourse/{current}/{limit}")
    public R getCourseFrontList(@PathVariable long current, @PathVariable long limit,
                                @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<EduCourse> page = new Page<>(current, limit);

        Map<String, Object> map = courseService.getCourseFrontList(page, courseQueryVo);
        return R.ok().data(map);
    }

    // 根据课程id得到课程详细信息
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //1 根据课程id得到课程详细信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        // 2 得到章节小节
        List<ChapterVo> chapterVideo = chapterService.getChapterVideo(courseId);
        // 3 判断该课程是否被购买过
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken)) {
            return R.error().code(28004).message("请先登录");
        }
        boolean isBuy = orderCourseClient.isBuyCourse(courseId, memberIdByJwtToken);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideo", chapterVideo).data("isBuy", isBuy);
    }

    // 根据课程id得到课程信息
    @GetMapping("getOrderCourseVo/{courseId}")
    public OrderCourseVo getOrderCourse(@PathVariable("courseId") String courseId) {
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        OrderCourseVo orderCourseVo = new OrderCourseVo();
        BeanUtils.copyProperties(courseWebVo,orderCourseVo);
        return orderCourseVo;
    }
}
