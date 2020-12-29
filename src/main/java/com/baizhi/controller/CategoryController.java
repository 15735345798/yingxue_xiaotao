package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author:xiaotao
 * @time 2020/12/22-16:15
 */

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @ResponseBody
    @RequestMapping("oneCategory")
    public HashMap<String, Object> queryoneCategory(Integer page, Integer rows){
        return categoryService.oneCategory(page, rows);
    }
    @ResponseBody
    @RequestMapping("twoCategory")
    public HashMap<String, Object> querytwoCategory(Integer page, Integer rows,String parent_id){
        return categoryService.twoCategory(page, rows,parent_id);
    }
    @ResponseBody
    @RequestMapping("edit")
    public void edit(Category category, String oper, HttpSession session,String parent_id){
        try {
            if(oper.equals("add")){
                categoryService.add(category,parent_id);
            }
            if(oper.equals("edit")){
                categoryService.edit(category);
            }
            if(oper.equals("del")){
                categoryService.del(category);
            }
        }catch (Exception e){
            e.printStackTrace();
            String message=e.getMessage();
            session.setAttribute("message",message);
        }
    }
}
