package com.example.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */@Mapper
public interface UserMapper extends BaseMapper<User> {

}
