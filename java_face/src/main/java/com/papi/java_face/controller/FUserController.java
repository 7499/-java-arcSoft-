package com.papi.java_face.controller;

import com.papi.java_face.dto.User;
import com.papi.java_face.entity.FUser;
import com.papi.java_face.service.FUserService;
import com.papi.java_face.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 20:52
 */
@Controller
@RequestMapping("/user")
public class FUserController {

    @Autowired
    private FUserService fUserService;


    @GetMapping("/demo")
    @ResponseBody
    public ResultUtil demo(@RequestParam("id") String id) {
        return ResultUtil.success(id);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultUtil register(@RequestBody User user) {
        return ResultUtil.success(fUserService.register(user));
    }


    @PostMapping("/login")
    @ResponseBody
    public ResultUtil login(@RequestBody User user) {
        return ResultUtil.success(fUserService.login(user));
    }


}
