package com.baizhi.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author AliyunOSSUtil
 * @time 2020/12/23-15:58
 */
public class AliyunOSSUtil {

    /*
    * 将文件上传至阿里云
    * 参数：
    *   headImg：MultipartFile类型的文件
    *   bucketName:存储空间名
    *   objectName:文件名
    * */
    public static void uploadBytesFile(MultipartFile headImg,String bucketName,String objectName){

        byte[] bytes =null;
        try {
            //将文件转为byte数组
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Endpoint以杭州为例，其它Region请按实际情况填写。Region：存储地址
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G6cLVApwjLrWduMaVdv";
        String accessKeySecret = "9dIWqe3nhINd4nPEnyMoXpwj60nIxZ";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        System.out.println(ossClient);
        // 上传Byte数组。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //视频删除
    public static void deleteFile(String bucketName,String objectName){
        // 上传Byte数组。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G6cLVApwjLrWduMaVdv";
        String accessKeySecret = "9dIWqe3nhINd4nPEnyMoXpwj60nIxZ";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //视频截取
    /*
     * 视频截取帧并上传至阿里云
     * 参数：
     *   bucketName:存储空间名
     *   videoObjectName:视频文件名
     *   coverObjectName:封面文件名
     * */
    public static void videocaptureFile(String bucketName,String videoObjectName,String coverObjectName) {
        // 上传Byte数组。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G6cLVApwjLrWduMaVdv";
        String accessKeySecret = "9dIWqe3nhINd4nPEnyMoXpwj60nIxZ";
        // 创建OSSClient实例。
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, videoObjectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);

        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传图片
        ossClient.putObject(bucketName, coverObjectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }



}
