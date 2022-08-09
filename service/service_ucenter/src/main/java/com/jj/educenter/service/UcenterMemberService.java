package com.jj.educenter.service;

import com.jj.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.educenter.entity.vo.LoginMemberVo;
import com.jj.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-08-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    // 用户登录
    String login(LoginMemberVo loginMemberVo);

    // 注册
    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer registerCount(String day);
}
