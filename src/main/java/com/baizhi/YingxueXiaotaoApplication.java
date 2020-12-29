package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@tk.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@MapperScan("com.baizhi.dao")
@SpringBootApplication
public class YingxueXiaotaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxueXiaotaoApplication.class, args);
    }

}
