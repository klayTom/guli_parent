package com.jj.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.commonutils.JwtUtils;
import com.jj.commonutils.R;
import com.jj.eduorder.entity.Order;
import com.jj.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-06
 */
@RestController
@RequestMapping("/eduorder/order")

public class OrderController {
    @Autowired
    private OrderService orderService;

    // 根据课程id创建订单
    @GetMapping("addOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        // 根据token获取用户id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        // 返回订单编号
        String orderNo = orderService.createOrder(courseId, memberIdByJwtToken);
        return R.ok().data("orderId", orderNo);
    }

    // 根据订单编号查询订单
    @GetMapping("getOrder/{orderId}")
    public R getOrder(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    // 根据课程id和用户id查询该课程是否被购买过
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId ) {
        if (null == courseId || null == memberId) {
            return false;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

