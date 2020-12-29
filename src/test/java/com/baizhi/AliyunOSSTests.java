package com.baizhi;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.util.AliyunOSSUtil;
import org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class AliyunOSSTests {

    //创建存储空间
    @Test
    public void testCreateBucket(){

        // Endpoint以杭州为例，其它Region请按实际情况填写。Region：存储地址
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G6cLVApwjLrWduMaVdv";
        String accessKeySecret = "9dIWqe3nhINd4nPEnyMoXpwj60nIxZ";

        //创建存储空间
        String bucketName = "jsfgdhsubhdbfj";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void testvfvfvv(){
        AliyunOSSUtil.deleteFile("xiaotao-yingxue","1608730732805-1608686465395-2222.jpg");
    }
}
