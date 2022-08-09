package com.jj.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jj.commonutils.UcenterVo;
import com.jj.commonutils.ordervo.OrderCourseVo;
import com.jj.eduorder.client.CourseClient;
import com.jj.eduorder.client.UcenterClient;
import com.jj.eduorder.entity.Order;
import com.jj.eduorder.mapper.OrderMapper;
import com.jj.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-08-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private CourseClient courseClient;


    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        // 根据课程id获取课程信息
        OrderCourseVo orderCourse = courseClient.getOrderCourse(courseId);
        // 根据用户id获取用户信息
        UcenterVo ucenterVo = ucenterClient.getInfoById(memberIdByJwtToken);

        Order order = new Order();
        order.setOrderNo(IdWorker.getIdStr());
        order.setCourseId(courseId);
        order.setCourseTitle(orderCourse.getTitle());
        order.setCourseCover(orderCourse.getCover());
        order.setTeacherName(orderCourse.getTeacherName());
        order.setTotalFee(orderCourse.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setEmail(ucenterVo.getEmail());
        order.setNickname(ucenterVo.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
