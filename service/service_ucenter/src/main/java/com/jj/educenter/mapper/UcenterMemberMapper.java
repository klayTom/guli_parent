package com.jj.educenter.mapper;

import com.jj.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2022-08-02
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCount(String day);
}
