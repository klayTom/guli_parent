package com.jj.edu.client;

import com.jj.commonutils.UcenterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getInfoById/{memberId}")
    UcenterVo getInfoById(@PathVariable("memberId") String memberId);
}
