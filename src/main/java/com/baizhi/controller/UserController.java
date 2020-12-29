package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.po.UserOOP;
import com.baizhi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/21-16:58
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("queryUserPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return userService.queryUserPage(page, rows);
    }


    /*
     * 添加带图片的数据
     * 1.数据入库
     *    问题：
     *          文件上传问题
     *         图片地址有问题
     *  返回数据id
     * 2.文件上传
     * 3.修改图片数据
     * */
    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user, String oper){
        String id =null;
        if(oper.equals("add")){
            id = userService.add(user);
        }
        if(oper.equals("edit")){
            id=userService.edit(user);
        }
        if(oper.equals("del")){
            userService.del(user);
        }
        return id;
    }

    @ResponseBody
    @RequestMapping("uploadUserCover")
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request){
        userService.uploadUserCover(headImg, id, request);
    }

    @ResponseBody
    @RequestMapping("update")
    public void uploadUserCover(String id){
        userService.update(id);
    }
    @ResponseBody
    @RequestMapping("getUserData")
    public HashMap<String, Object> getUserData(){

        return userService.yueryByUser();
    }
    @ResponseBody
    @RequestMapping("getUserChina")
    public List<UserOOP> getUserChina(){
        return userService.queryByUserPPo();
    }
}
