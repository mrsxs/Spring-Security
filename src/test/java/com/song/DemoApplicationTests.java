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
        String s = JwtUtils.generateJwtWithSubject("song");
        System.out.println(s);
        Claims claims = JwtUtils.parseJWT(s);
        System.out.println(claims.getSubject());


    }
}
