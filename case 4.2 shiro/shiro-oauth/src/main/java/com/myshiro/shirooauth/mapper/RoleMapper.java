package com.myshiro.shirooauth.mapper;

import com.myshiro.shirooauth.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/24 22:07
 * @Version 1.0
 **/
@Mapper
@Component
public interface RoleMapper {

    @Select("SELECT r.roleid,u.userid,r.roletype,r.roledesc FROM role r,user_roles u WHERE u.roleid = r.roleid and u.userid = #{userid}")
    @Results( id="roleanduser",value={
            @Result(column="roleid", property="roleid", id=true),
            @Result(column="roletype", property="roletype"),
            @Result(column="roledesc", property="roledesc"),
            @Result(column="userid", property="userid")
    })
    List<Role> findRolesByUserid(@Param("userid") int userid);
}
