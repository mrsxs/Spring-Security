package com.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 测试控制器
 *
 * @author song
 * @date 2024/03/31
 */
@Controller
public class TestController {


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test message";
    }

}
