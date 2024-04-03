package com.song;

import com.song.service.IUserService;
import com.song.utlis.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.list());
    }

    @Test
    void passWord() {
        System.out.println(passwordEncoder.encode("123456"));


    }

    @Test
    void name() {
        String s = JwtUtils.generateJwtWithSubject("123");

        Claims claims = JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjZWMzMzRjYy1iOGZhLTQ5ZTktOTQxYy0xMGE4NWFlNThmNWIiLCJpYXQiOjE3MTIwNDMzNTEsImV4cCI6MTcxMjA0MzM1NH0.znvB-mGJC_KuvwceW_klDAZKlBRPB9MQ_FiyRkgs5fU");
        System.out.println(claims.getSubject());
    }
}
