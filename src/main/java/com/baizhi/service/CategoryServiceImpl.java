package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.AdminExample;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @author:xiaotao
 * @time 2020/12/22-11:45
 */
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public HashMap<String, Object> oneCategory(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Category> categorys = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categorys);
        //查询总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }

    @Override
    public HashMap<String, Object> twoCategory(Integer page, Integer rows,String parent_id) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parent_id);
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Category> categorys = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categorys);
        //查询总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);
        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);
        return map;
    }
    @AddLog(value="添加类别")
    @Override
    public void add(Category category,String parent_id) {
        if(parent_id!=null){
            category.setLevels(2);
            category.setParentId(parent_id);
        }else {
            category.setLevels(1);
        }
        category.setId(UUIDUtil.getUUID());
        System.out.println(category);
        categoryMapper.insertSelective(category);
    }
    @AddLog(value="修改类别")
    @Override
    public void edit(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }
    @AddLog(value="删除类别")
    @Override
    public void del(Category category) {
        Category c=categoryMapper.selectByPrimaryKey(category);
            if(c.getParentId()==null){
                CategoryExample example = new CategoryExample();
                example.createCriteria().andParentIdEqualTo(c.getId());
                List<Category> admins = categoryMapper.selectByExample(example);
                if (admins==null){
                    categoryMapper.deleteByPrimaryKey(category);
                }else {
                    throw new RuntimeException("不能删除！！！请重试");
                }
            }else {
                //查询下面有没有视频
                System.out.println("这是二级类别！！可删");
                categoryMapper.deleteByPrimaryKey(category);
            }

    }
}
