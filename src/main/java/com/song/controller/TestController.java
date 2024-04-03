package com.song.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 测试控制器
 *
 * @author song
 * 2021/04/03
 */
@Controller
public class TestController {


    @RequestMapping("/test")
    @ResponseBody
    @PreAuthorize("hasAuthority('test')")
    public String test() {
        return "test message";
    }

    @RequestMapping("/test2")
    @ResponseBody
    @PreAuthorize("@ex.hasAuthority('test2')")
    public String test2() {
        return "test message2";
    }

    @RequestMapping("/test3")
    @ResponseBody
    @PreAuthorize("hasAuthority('test3')")
    public String test3() {
        return "test message3";
    }
    @RequestMapping("/test4")
    @ResponseBody
    public String test4() {
        return "test message4";
    }

}
