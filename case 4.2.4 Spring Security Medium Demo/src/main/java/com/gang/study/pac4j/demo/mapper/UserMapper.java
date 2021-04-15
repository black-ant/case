package com.gang.study.pac4j.demo.mapper;

import com.gang.study.pac4j.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id , username , password from user where username = #{username}")
    User loadUserByUsername(@Param("username") String username);

}
