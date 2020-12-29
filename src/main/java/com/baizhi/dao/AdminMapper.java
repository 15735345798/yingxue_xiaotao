package com.baizhi.dao;

import com.baizhi.entity.Admin;

import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {
    //登录
    Admin LoginAdmin(String username);
}