package com.baizhi.service;

import java.util.HashMap;

public interface LogService {
    // 日志分页查询
    HashMap<String,Object> queryLogPage(Integer page, Integer rows);
}
