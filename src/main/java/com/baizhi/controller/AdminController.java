package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


/**
 * @author:xiaotao
 * @time 2020/12/18-17:26
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService us;

    @RequestMapping("/login")
    public String login(Admin a, HttpSession session,String Code){
        try {
            Admin admin= us.LoginAdmin(a);
            System.out.println(Code);
            session.setAttribute("admin",admin);
            if(Code.equals(session.getAttribute("securityCode")) == false) throw new RuntimeException("验证码错误！！！");
            return "redirect:/main/main.jsp";
        }catch (Exception e){
            e.printStackTrace();
            String message=e.getMessage();
            session.setAttribute("message",message);
            return "redirect:/index.jsp";
        }
    }
    //获取验证码
    @RequestMapping("/getImageCode")
    public  void getImageCode(HttpSession session, HttpServletResponse response){
        ImageCodeUtil imageCodeUtil=new ImageCodeUtil();
        String securityCode = imageCodeUtil.getSecurityCode();
        System.out.println(securityCode);
        session.setAttribute("securityCode", securityCode);
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write(imageCodeUtil.createImage(securityCode), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //退出
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
    //查询所有管理员
    @ResponseBody
    @RequestMapping("queryUserPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return us.queryUserPage(page, rows);
    }
    @ResponseBody
    @RequestMapping("edit")
    public void edit(Admin admin, String oper,HttpSession session){
        try {
            if(oper.equals("add")){
                us.add(admin);
            }
            if(oper.equals("edit")){
                us.edit(admin);
            }
            if(oper.equals("del")){
                us.del(admin);
            }
        }catch (Exception e){
            e.printStackTrace();
            String message=e.getMessage();
            session.setAttribute("message",message);
        }
    }

}
