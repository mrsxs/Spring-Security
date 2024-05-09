package com.song.controller;

import com.song.pojo.Result;
import com.song.pojo.User;
import com.song.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor

public class LoginController {

    private final IUserService userService;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user, HttpServletResponse response) {


        return userService.login(user, response);
    }

    @PostMapping("/user/logout")
    public Result logout() {
        return userService.logout();
    }
}
