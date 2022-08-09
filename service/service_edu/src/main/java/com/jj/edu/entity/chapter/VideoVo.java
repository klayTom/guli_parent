package com.jj.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoVo {
    private String id;

    private String title;

    private Integer isFree;

    private String videoSourceId;
}
