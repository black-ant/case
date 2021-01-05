package com.myshiro.provide.mapper;

import com.myshiro.provide.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/21 22:47
 * @Version 1.0
 **/
@Mapper
@Component
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = '${username}'")
    User findByUsername(@Param("username") String username);
}
