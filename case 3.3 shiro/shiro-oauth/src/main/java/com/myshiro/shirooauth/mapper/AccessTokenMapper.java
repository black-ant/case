package com.myshiro.shirooauth.mapper;

import com.myshiro.shirooauth.entity.AccessToken;
import com.myshiro.shirooauth.entity.Client;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/5 21:38
 * @Version 1.0
 **/
@Mapper
@Component
public interface AccessTokenMapper {

    @Delete("DELETE FROM access_token WHERE sn = #{id}")
    int deleteClient(@Param("id") Long id);

    @Select("SELECT * FROM  access_token WHERE sn = #{id}")
    AccessToken findOne(@Param("id") Long id);

    @Select("SELECT * FROM  access_token WHERE accesscode = #{code}")
    AccessToken findOneByCode(@Param("code") String code);

    @Select("SELECT * FROM  access_token WHERE username = #{username}")
    AccessToken findOneByUsername(@Param("username") String username);

    @Select("SELECT * FROM access_token")
    List<AccessToken> findAll();

    @Select("SELECT * FROM access_token WHERE accesstoken = #{accesstoken}")
    AccessToken findByAccessToken(@Param("accesstoken") String accesstoken);

    @Select("SELECT count(accesscode) FROM access_token WHERE accesscode = #{accesscode}")
    int countOneCode(@Param("accesscode") String accesscode);

    @Select("SELECT count(accesstoken) FROM access_token WHERE accesstoken = #{accesstoken}")
    int countOneTokenn(@Param("accesstoken") String accesstoken);

    @Select("UPDATE access_token SET accesstoken = #{accesstoken} WHERE username = #{username}")
    AccessToken updateTokem(@Param("accesstoken") String accesstoken,@Param("username") String username);

    @Insert({ "insert into access_token(accesscode, accesstoken, userid, username ,startdate,outdate) values(#{accesscode}, #{accesstoken}, #{userid}, #{username}, #{startdate, jdbcType=TIMESTAMP}, #{outdate, jdbcType=TIMESTAMP})" })
    @Options(useGeneratedKeys = true, keyProperty = "sn")
    int insertTokem( AccessToken accesstoken);
}
