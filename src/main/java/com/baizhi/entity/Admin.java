package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "admin")
@Data
@NoArgsConstructor//无参
@AllArgsConstructor//有参
public class Admin implements Serializable {
    @Excel(name = "id")
    @Id
    private String id;
    @Excel(name = "username")
    private String username;
    @Excel(name = "password")
    private String password;
    @Excel(name = "status")
    private String status;
    @Excel(name = "salt")
    private String salt;

}