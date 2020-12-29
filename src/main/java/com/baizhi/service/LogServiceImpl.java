package com.baizhi.service;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.entity.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/27-16:38
 */
@Transactional
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public HashMap<String, Object> queryLogPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",logs);
        //查询总条数
        int records =logMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }
}
