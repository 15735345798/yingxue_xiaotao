package com.baizhi.service;


import com.baizhi.entity.Category;

import java.util.HashMap;

public interface CategoryService {
    // 一级分页查询
    HashMap<String,Object> oneCategory(Integer page, Integer rows);
    // 二级分页查询
    HashMap<String,Object> twoCategory(Integer page, Integer rows,String parent_id);
    //添加
    void add(Category category,String parent_id);
    //更新
    void edit(Category category);
    //删除
    void del(Category category);
}
