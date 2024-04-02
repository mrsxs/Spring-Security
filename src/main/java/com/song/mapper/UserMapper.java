package com.song.mapper;

import com.song.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-04-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
