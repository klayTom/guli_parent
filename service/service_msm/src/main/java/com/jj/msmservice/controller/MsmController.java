package com.jj.msmservice.controller;

import com.jj.commonutils.R;
import com.jj.msmservice.service.MsmService;
import com.jj.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private JavaMailSender javaMailSender;

    //读取yml文件中username的值并赋值给form
    @Value("${spring.mail.username}")
    private String from;

    @GetMapping("send/{email}")
    public R send(@PathVariable String email) {
        //从redis中获取验证码，如果获取到就直接返回
        String code = redisTemplate.opsForValue().get(email);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //如果redis获取不到，就进行阿里云发送
        //生成随机值，传递阿里云进行发送
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom(from);
        // 设置邮件接收者
        message.setTo(email);
        // 设置邮件的主题
        message.setSubject("登录验证码");
        code =RandomUtil.getFourBitRandom();
        message.setText(code);

        try {
            javaMailSender.send(message);
            //发送成功，把发送成功的验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return R.ok();
        } catch (MailException e) {
            e.printStackTrace();
            return R.error().message("邮件发送失败");
        }
    }
}
