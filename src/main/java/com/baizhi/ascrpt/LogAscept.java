package com.baizhi.ascrpt;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.AdminMapper;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.util.UUIDUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;



/**
 * @author:xiaotao
 * @time 2020/12/27-15:53
 */
@Aspect   //切面类
@Configuration   //配置类 将该类将给工厂
public class LogAscept {
    @Resource
    HttpServletRequest request;
    @Autowired
    LogMapper logMapper;
    //切增删改
    //@Around("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.query*(..))")
    //切方法

    @Around("@annotation(com.baizhi.annotation.AddLog)")   //切注解
    public Object AddLog(ProceedingJoinPoint proceedingJoinPoint){

        //谁  时间  操作(哪个方法)   是否成功
        //获取管理员
        Admin admin = (Admin)request.getSession().getAttribute("admin");

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();


        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //通过类对象获取注解类
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取方法注解上的value属性的描述信息
        String methodDescription = addLog.value();

        Object result=null;
        String message=null;
        try {
            //放行方法  获取方法的返回值
            result = proceedingJoinPoint.proceed();
            message="success";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message="error";
        }
        //日志信息入库
        Log log = new Log(UUIDUtil.getUUID(),admin.getUsername(),new Date(),methodName+"("+methodDescription+")",message);
        logMapper.insertSelective(log);
        return null;
    }


}

