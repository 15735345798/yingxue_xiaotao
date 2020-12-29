package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import com.baizhi.po.UserOOP;
import com.baizhi.po.UserPO;
import com.baizhi.po.UserPPO;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/21-16:12
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

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
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);

        //遍历集合
        for (User user : users) {
            //根据用户id查询学分  redis
            String id = user.getId();
            //查询学分并赋值
            user.setScore("88");
        }

        map.put("rows",users);

        //查询总条数
        int records = userMapper.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }
    @AddLog(value="添加用户")
    @Override
    public String add(User user) {
        user.setId(UUIDUtil.getUUID());
        user.setStatus("1");
        user.setCreateDate(new Date());

        userMapper.insertSelective(user);
        return user.getId();
    }

    @Override
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request) {
            //1.获取文件名
            String filename = headImg.getOriginalFilename();
            //图片拼接时间戳
            String newName = new Date().getTime() + "-" + filename;

            //2.根据相对路径获取绝对路径
            String realPath = request.getServletContext().getRealPath("/upload/cover");

            //获取文件夹
            File file = new File(realPath);
            //判断文件夹是否存在
            if (!file.exists()) {
                file.mkdirs();//创建文件夹
            }

            //3.文件上传
            try {
                headImg.transferTo(new File(realPath, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            User user = new User();
            user.setId(id);
            user.setHeadImg(newName);

            //4.修改数据
            userMapper.updateByPrimaryKeySelective(user);
    }
    @AddLog(value="修改用户")
    @Override
    public String edit(User user) {
        if(user.getHeadImg()==""){
            User u=userMapper.selectByPrimaryKey(user);
            user.setHeadImg(u.getHeadImg());
            userMapper.updateByPrimaryKeySelective(user);
            return null;
        }
        User u=userMapper.selectByPrimaryKey(user);
        File f = new File("src/main/webapp/upload/cover/"+u.getHeadImg());
        f.delete();
        userMapper.updateByPrimaryKeySelective(user);
        return user.getId();
    }
    @AddLog(value="删除用户")
    @Override
    public void del(User user) {
        User u=userMapper.selectByPrimaryKey(user);
        File f = new File("src/main/webapp/upload/cover/"+u.getHeadImg());
        f.delete();
        userMapper.deleteByPrimaryKey(user);
    }

    @Override
    public void update(String id) {
        User user =new User();
        user.setId(id);
        User u=userMapper.selectByPrimaryKey(user);
        if(u.getStatus().equals("1")){
            user.setStatus("2");
        }else {
            user.setStatus("1");
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public HashMap<String, Object> yueryByUser() {
        HashMap<String, Object> map = new HashMap<>();
        List<UserPO> userPOS=userMapper.yueryByUser();
        ArrayList<String> month=new ArrayList<>();
        ArrayList<Integer> count=new ArrayList<Integer>();
        for(UserPO userPO:userPOS){
            month.add(userPO.getMonth());
            count.add(userPO.getCount());
        }
        map.put("month",month);
        map.put("count",count);
        return map;
    }

    @Override
    public List<UserOOP> queryByUserPPo() {
        List<UserPPO> nan=userMapper.queryByUserPPo("男");
        List<UserPPO> nv=userMapper.queryByUserPPo("女");
        UserOOP znan=new UserOOP("男",nan);
        UserOOP znv=new UserOOP("女",nv);
        ArrayList<UserOOP> list=new ArrayList<>();
        list.add(znan);
        list.add(znv);

        return list;
    }
}
