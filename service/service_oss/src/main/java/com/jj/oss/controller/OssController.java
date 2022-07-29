package com.jj.oss.controller;

import com.jj.commonutils.R;
import com.jj.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "oss模块")
@Controller
@RequestMapping("/eduoss/fileUpload")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @ResponseBody
    @PostMapping
    public R upload(MultipartFile file) {
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }
}