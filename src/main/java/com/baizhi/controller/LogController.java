package com.baizhi.controller;

import com.baizhi.service.LogService;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author:xiaotao
 * @time 2020/12/27-16:45
 */
@Controller
@RequestMapping("/log")
public class LogController {
    @Resource
    LogService logService;
    @ResponseBody
    @RequestMapping("queryLogPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return logService.queryLogPage(page, rows);
    }
}
