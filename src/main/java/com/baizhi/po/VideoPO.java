package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:xiaotao
 * @time 2020/12/26-15:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPO {
    private String id;
    private String title;
    private String description;
    private String videoPath;
    private String coverPath;
    private String uploadTime;
    private String categoryId;
    private String groupId;
    private String userId;
    private String headImg;
    private String cateName;
}
