package com.song.mapper;

import com.song.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-04-03
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermissionByUserId(Integer userId);
}
