package com.jj.edu.client;

import org.springframework.stereotype.Component;

@Component
public class OrderCourseClientImpl implements OrderCourseClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
