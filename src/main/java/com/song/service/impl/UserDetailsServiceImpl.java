package com.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.song.mapper.MenuMapper;
import com.song.mapper.UserMapper;
import com.song.pojo.LoginUser;
import com.song.pojo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户详细信息serviceimpl
 *
 * @author song
 * @date 2024/04/01
 */

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
 private final MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        //查询对应的权限
        //todo 查询用户权限
        List<String> list = menuMapper.selectPermissionByUserId(user.getId());

        //封装userDetails 返回



        return new LoginUser(user, list);
    }
}
