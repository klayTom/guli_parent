package com.jj.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jj.commonutils.JwtUtils;
import com.jj.educenter.entity.UcenterMember;
import com.jj.educenter.entity.vo.LoginMemberVo;
import com.jj.educenter.entity.vo.RegisterVo;
import com.jj.educenter.mapper.UcenterMemberMapper;
import com.jj.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.educenter.utils.MD5;
import com.jj.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2022-08-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginMemberVo loginMemberVo) {
        // 判断email和password是否为空
        String email = loginMemberVo.getEmail();
        String password = loginMemberVo.getPassword();

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登陆失败");
        }

        // 判断 eamil 是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (!email.equals(member.getEmail())) {
            throw new GuliException(2001, "登录失败");
        }

        // 判断password 是否正确
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(2001, "密码不正确");
        }

        // 判断是否被禁用
        if (member.getIsDisabled()) {
            throw new GuliException(2001, "登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String email = registerVo.getEmail(); //邮箱
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(org.springframework.util.StringUtils.isEmpty(email) || org.springframework.util.StringUtils.isEmpty(password)
                || org.springframework.util.StringUtils.isEmpty(code) || org.springframework.util.StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)) {
            throw new GuliException(20001,"注册失败");

        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new GuliException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://guli-edu-20201.oss-cn-beijing.aliyuncs.com/2020/10/08/3a6bf3d4a85f415693e062db5fb17df8file.png");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer registerCount(String day) {
        Integer count = baseMapper.registerCount(day);
        return count;
    }
}
