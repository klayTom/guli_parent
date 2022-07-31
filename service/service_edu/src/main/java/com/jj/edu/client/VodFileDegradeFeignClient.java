package com.jj.edu.client;

import com.jj.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteAlyVodBatch(List<String> videoIds) {
        return R.error().message("删除视频出错了");
    }
}
