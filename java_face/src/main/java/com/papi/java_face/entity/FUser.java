package com.papi.java_face.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 20:47
 */
@Data
@TableName("f_user")
public class FUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private String account;
    private String password;
    private String userName;
    private Integer sex;
    private Integer status;

}
