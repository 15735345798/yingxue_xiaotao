package com.baizhi.service;

import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.UserExample;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/18-17:17
 */
@Transactional
@Service
public class AdminSerivceImpl implements AdminService {

    @Autowired
    AdminMapper am;

    @Override
    public Admin LoginAdmin(Admin admin) {
        Admin a1=am.LoginAdmin(admin.getUsername());
        if(a1==null)throw new RuntimeException("没有此管理员");
        if(a1.getPassword().equals(admin.getPassword()) == false) throw new RuntimeException("密码错误！！！");
        return a1;
    }
    @Override
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows) {
        // Integer page, Integer rows(每页展示条数)
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();

        //设置当前页
        map.put("page",page);
        //创建条件对象
        UserExample example = new UserExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Admin>admins = am.selectByExampleAndRowBounds(example, rowBounds);


        map.put("rows",admins);

        //查询总条数
        int records = am.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }

    @Override
    public void add(Admin admin) {
        admin.setId(UUIDUtil.getUUID());
        admin.setStatus("2");
        admin.setSalt(Md5Utils.getSalt(4));
        am.insertSelective(admin);

    }

    @Override
    public void edit(Admin admin) {
       if(admin.getId().equals("1"))throw new RuntimeException("无法更改");
        am.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void del(Admin admin) {
        if(admin.getId().equals("1"))throw new RuntimeException("无法删除");
        am.deleteByPrimaryKey(admin);
    }
}
