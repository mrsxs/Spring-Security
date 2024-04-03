package com.song.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('admin')")
    public String test() {
        return "test message";
    }

    @RequestMapping("/test2")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public String test2() {
        return "test message2";
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String test3() {
        return "test message3";
    }

}
