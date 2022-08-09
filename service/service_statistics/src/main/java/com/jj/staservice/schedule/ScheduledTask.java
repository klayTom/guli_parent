package com.jj.staservice.schedule;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jj.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduled(){
        DateTime yesterday = DateUtil.yesterday();
        String format = DateUtil.format(yesterday, "yyyy-MM-dd");
        dailyService.createStatisticsByDay(format);
    }
}
