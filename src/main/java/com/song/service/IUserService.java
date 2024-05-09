package com.song.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.song.pojo.Result;
import com.song.pojo.User;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author song
 * @since 2024-04-01
 */
public interface IUserService extends IService<User> {


    Result login(User user, HttpServletResponse response);

    /**
     * 注销
     *
     * @return {@link Result}
     */
    Result logout();

}
