package com.jj.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.staservice.client.UcenterClient;
import com.jj.staservice.entity.StatisticsDaily;
import com.jj.staservice.mapper.StatisticsDailyMapper;
import com.jj.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-08-07
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        // 添加之前 先删除表中存在的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        // 远程调用 查询某一天的注册人数
        Integer count = ucenterClient.registerCount(day);

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(count);
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setDateCalculated(day);

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getSta(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(wrapper);
        // 封装日期
        List<String> dateList = new ArrayList<>();
        // 封装查询类型
        List<Integer> numList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("numList", numList);
        return map;
    }
}
