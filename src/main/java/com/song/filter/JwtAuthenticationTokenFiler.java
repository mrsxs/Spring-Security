package com.song.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.song.pojo.LoginUser;
import com.song.utlis.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationTokenFiler extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        //获取cookie中的token
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (StrUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String uuid;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            uuid = claims.getSubject();

        } catch (Exception e) {
            throw new RuntimeException("token非法");
        }

        //从redis中获取用户信息
        String s = redisTemplate.opsForValue().get("login:" + uuid);
        if (Objects.isNull(s)) {
            throw new RuntimeException("用户未登录");
        }
        //转成对象
        LoginUser loginUser = JSONUtil.toBean(s, LoginUser.class);
        //将用户信息存入SecurityContextHolder中
        //创建UsernamePasswordAuthenticationToken 必须使用三个参数的构造方法 存入认证信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);

    }
}
