package com.baizhi.commoResult;

import com.baizhi.po.VideoPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/26-17:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommoResult {
    private String message;
    private String status;
    private Object date;

    public CommoResult success(String message, String status, Object date){
         CommoResult commResult=new CommoResult();
         commResult.setMessage(message);
         commResult.setDate(date);
         commResult.setStatus(status);
        return commResult;
    }
    public CommoResult filed(String status, Object date){
        CommoResult commResult=new CommoResult();
        commResult.setMessage("查询失败");
        commResult.setDate(date);
        commResult.setStatus(status);
        return commResult;
    }



}
