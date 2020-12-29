package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    //管理员登录
    Admin LoginAdmin(Admin admin);
    // 分页查询
    HashMap<String,Object> queryUserPage(Integer page, Integer rows);
    //添加
    void add(Admin admin);
    //更新
    void edit(Admin admin);
    //删除
    void del(Admin admin);
}
