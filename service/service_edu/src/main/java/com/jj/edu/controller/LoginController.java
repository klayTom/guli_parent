package com.jj.edu.controller;

import com.jj.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
@CrossOrigin // 解决跨域问题
public class LoginController {
    @PostMapping("login")
    public R login(){

        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://avatars.githubusercontent.com/u/94097480");
    }
}
