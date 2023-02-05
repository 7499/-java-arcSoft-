package com.papi.java_face.dto;

import lombok.Data;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 21:56
 */
@Data
public class User {

    private String account;
    private String password;
    private String userName;
    private Integer sex;
    private Integer status;

    private String faceInfoBase64;

}
