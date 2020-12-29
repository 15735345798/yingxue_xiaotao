package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author:xiaotao
 * @time 2020/12/23-18:12
 */
@Controller
@RequestMapping("/video")
public class VideoController {
    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("queryVideoPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return videoService.queryVideoPage(page, rows);
    }
    @ResponseBody
    @RequestMapping("edit")
    public String edit(Video video, String oper){
        String id =null;
        if(oper.equals("add")){
            id = videoService.add(video);
        }
        if(oper.equals("edit")){
            id=videoService.edit(video);
        }
        if(oper.equals("del")){
            videoService.del(video);
        }
        return id;
    }
    @ResponseBody
    @RequestMapping("upload123")
    public void uploadVideoCover(MultipartFile videoPath, String id, HttpServletRequest request){
        videoService.uploadVideoCover(videoPath,id,request);
    }
}
