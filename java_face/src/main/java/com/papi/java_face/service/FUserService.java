package com.papi.java_face.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.papi.java_face.dto.User;
import com.papi.java_face.entity.FUser;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 20:50
 */
public interface FUserService extends IService<FUser> {


    String register(User fUser);

    String login(User fUser);

}
