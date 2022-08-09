package com.jj.educenter.controller;


import com.jj.commonutils.JwtUtils;
import com.jj.commonutils.R;
import com.jj.commonutils.UcenterVo;
import com.jj.educenter.entity.UcenterMember;
import com.jj.educenter.entity.vo.LoginMemberVo;
import com.jj.educenter.entity.vo.RegisterVo;
import com.jj.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/educenter/member")

public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 用户登录
    @PostMapping("login")
    public R login(@RequestBody LoginMemberVo loginMemberVo) {
        String token = memberService.login(loginMemberVo);
        return R.ok().data("token", token);
    }

    // 用户注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token得到用户信息
    @GetMapping("getUserInfo")
    public R getInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    // 根据memberI查询用户信息
    @GetMapping("getInfoById/{memberId}")
    public UcenterVo getInfoById(@PathVariable String memberId) {
        UcenterMember member = memberService.getById(memberId);
        UcenterVo ucenterVo = new UcenterVo();
        BeanUtils.copyProperties(member, ucenterVo);
        return ucenterVo;
    }

    // 查询注册人数
    @GetMapping("registerCount/{day}")
    public Integer registerCount(@PathVariable("day") String day) {
        Integer count = memberService.registerCount(day);
        return count;
    }
}

