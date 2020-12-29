package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPO;
import com.baizhi.po.VideoVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {
    // 分页查询
    HashMap<String,Object> queryVideoPage(Integer page, Integer rows);
    //添加
    String add(Video video);
    //文件上传
    void uploadVideoCover(MultipartFile videoPath, String id, HttpServletRequest request);
    //更新
    String edit(Video video);
    //删除
    void del(Video video);
    //前台接口
    List<VideoVO> queryByReleaseTime();


}
