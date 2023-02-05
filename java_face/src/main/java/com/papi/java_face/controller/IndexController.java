package com.papi.java_face.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 22:31
 */
@Controller
public class IndexController {

    //通过controller返回html界面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
