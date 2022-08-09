package com.jj.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/educenter/member/registerCount/{day}")
    Integer registerCount(@PathVariable("day") String day);
}
