package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.dao.AdminMapper;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import com.baizhi.service.AdminSerivceImpl;
import com.baizhi.service.VideoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class YingxueXiaotaoApplicationTests {

    @Resource
    AdminMapper adminMapper;
    @Resource
    AdminSerivceImpl adminSerivce;
    @Test
    void contextLoads() {
        //条件对象
        AdminExample example=new AdminExample();
        //取参
        example.createCriteria().andUsernameEqualTo("admin");
        //查询
        List<Admin> admin=adminMapper.selectByExample(example);
        System.out.println(admin);
    }
    @Test
    void test1() {
        Admin admin=new Admin();
        admin.setUsername("admin");
        System.out.println(adminSerivce.LoginAdmin(admin));
    }
    //导入
    @Test
    void Esdsdsf() throws IOException {
        ArrayList<Admin> list=new ArrayList<>();
        list.add(new Admin("1","111","111","1","dsds"));
        list.add(new Admin("2","222","222","2","lkjh"));
        list.add(new Admin("3","333","333","3","cxzs"));
        //设置到处参数
        ExportParams exportParams =new ExportParams("xxx班","学生");

        //到出表格
        Workbook workbook= ExcelExportUtil.exportExcel(exportParams,Admin.class,list);

        workbook.write(new FileOutputStream(new File("D://easyPoi.xls")));
    }
    //导入
    @Test
    void test12()  {
        //设置导出参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //标题所占行
        params.setHeadRows(1);  //表头所占行

        //导入数据
        List<Admin> admins = null;
        try {
            admins = ExcelImportUtil.importExcel(new FileInputStream(new File("D://easyPoi.xls")), Admin.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //遍历
        admins.forEach(admincc -> System.out.println(admincc));
    }
    @Resource
    VideoService videoService;

    @Test
    public void tst(){
        System.out.println(videoService.queryByReleaseTime());
    }
    @Resource
    UserMapper userMapper;

    @Test
    public void tstss(){
        System.out.println(userMapper.queryByUserPPo("男"));
    }
}
