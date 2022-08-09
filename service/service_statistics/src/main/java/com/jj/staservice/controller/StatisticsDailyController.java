package com.jj.staservice.controller;


import com.jj.commonutils.R;
import com.jj.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-07
 */
@RestController
@RequestMapping("/staservice/statistics")

public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("getSta/{type}/{begin}/{end}")
    public R getSta(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String,Object> map = dailyService.getSta(type, begin, end);
        return R.ok().data(map);
    }
}

