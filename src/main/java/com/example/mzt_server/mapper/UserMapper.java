package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小程序用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}