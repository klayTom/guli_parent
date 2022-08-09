package com.jj.eduorder.controller;


import com.jj.commonutils.R;
import com.jj.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2022-08-06
 */
@RestController
@RequestMapping("/eduorder/payLog")

public class PayLogController {
    @Autowired
    private PayLogService payService;
    /**
     * 生成二维码
     *
     * @return
     */
    // 生成二维码
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        Map map = payService.createNative(orderNo);
        System.out.println("======"+map);
        return R.ok().data(map);
    }

    // 查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }


}

