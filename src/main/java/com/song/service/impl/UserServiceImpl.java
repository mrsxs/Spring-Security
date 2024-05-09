package com.song.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.mapper.UserMapper;
import com.song.pojo.LoginUser;
import com.song.pojo.Result;
import com.song.pojo.User;
import com.song.service.IUserService;
import com.song.utlis.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author song
 * @since 2024-04-01
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


  private final AuthenticationManager authenticationManager;

  private final StringRedisTemplate redisTemplate;

  @Override
  public Result login(User user, HttpServletResponse response) {
    // authentication 进行认证
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
    //如果没通过 给出提示
    if (Objects.isNull(authenticate)) {
      return Result.fail("用户名或密码错误");
    }
    //如果通过 返回token
    LoginUser principal = (LoginUser) authenticate.getPrincipal();
    String uuid = StrUtil.uuid();
    principal.setToken(uuid);
    String token = JwtUtils.generateJwtWithSubject(uuid);
    String jsonStr = JSONUtil.toJsonStr(principal);

    //把完整的用户信息返回存入redis
    redisTemplate.opsForValue().set("login:" + uuid, jsonStr);
    // 设置过期时间
    redisTemplate.expire("login:" + uuid, 30, TimeUnit.MINUTES);

    //写入cookie 用于前端获取
//        写入请求头
    Cookie cookie = new Cookie("token", token);
// 设置Cookie的最大生存时间，单位为秒
    cookie.setMaxAge(60 * 60 * 24); // 24 hours
// 设置Cookie的路径
    cookie.setPath("/");
// 将Cookie添加到响应中
    response.addCookie(cookie);
    return Result.ok(token, "登录成功");
  }

  @Override
  public Result logout() {
    //获取当前登录用户
    LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String uuid = principal.getToken();

    //删除redis中的用户信息
    redisTemplate.delete("login:" + uuid);

    return Result.ok("注销成功");
  }
}
