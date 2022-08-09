package com.jj.educms.service;

import com.jj.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zjq
 * @since 2022-08-01
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getListBanner();
}
