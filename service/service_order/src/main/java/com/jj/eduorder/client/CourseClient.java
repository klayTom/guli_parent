package com.jj.eduorder.client;

import com.jj.commonutils.ordervo.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu", fallback = CourseClientImpl.class)
public interface CourseClient {
    @GetMapping("/edu/coursefront/getOrderCourseVo/{courseId}")
    OrderCourseVo getOrderCourse(@PathVariable("courseId") String courseId);
}
