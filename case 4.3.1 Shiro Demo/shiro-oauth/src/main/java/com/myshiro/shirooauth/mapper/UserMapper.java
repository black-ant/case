package com.myshiro.shirooauth.mapper;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/24 18:36
 * @Version 1.0
 **/
@Mapper
@Component
public interface UserMapper{

    @Select("SELECT * FROM user WHERE username = #{username}")
    @Results({
            @Result(property ="roles",column="userid",one =@One(select ="com.myshiro.shirooauth.mapper.RoleMapper.findRolesByUserid"))
    })
    User findByUsername(@Param("username") String username);



    @Select("SELECT * FROM role WHERE roleid = #{roleid}")
    Role findRolesByRoleid(@Param("roleid") int roleid);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsernametest(@Param("username") String username);
}