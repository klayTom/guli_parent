package com.jj.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.commonutils.R;
import com.jj.educms.entity.CrmBanner;
import com.jj.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/educms/bannerfront")

public class BannerFrontController {
    @Autowired
    CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.getListBanner();
        return R.ok().data("list",list);
    }
}

