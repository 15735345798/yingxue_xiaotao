package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/29-9:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOOP {
    private String sex;
    private List<UserPPO> citys;
}
