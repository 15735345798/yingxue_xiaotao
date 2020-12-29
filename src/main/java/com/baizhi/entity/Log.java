package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private String id;
    @Column(name = "adminName")
    private String adminname;
    @DateTimeFormat(pattern = "yyyy-MM-dd ")  //设置接受日期格式
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "optionTime")
    private Date optiontime;

    private String options;
    @Column(name = "isSuccess")
    private String issuccess;

}