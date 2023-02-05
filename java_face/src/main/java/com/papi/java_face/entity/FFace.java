package com.papi.java_face.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 21:01
 */
@Data
@TableName("f_face")
public class FFace {

    @TableId(value = "face_id", type = IdType.AUTO)
    private Integer faceId;
    private Integer userId;
    private String faceInfo; //base64


}
