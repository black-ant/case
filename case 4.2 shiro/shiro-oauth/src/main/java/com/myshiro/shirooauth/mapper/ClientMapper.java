package com.myshiro.shirooauth.mapper;

import com.myshiro.shirooauth.entity.Client;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 22:47
 * @Version 1.0
 **/
@Mapper
@Component
public interface ClientMapper {

    @Insert("")
    Client createClient(Client client);

    @Update("")
    Client updateClient(Client client);

    @Delete("DELETE FROM client WHERE id = #{id}")
    Client deleteClient(@Param("id") Long id);

    @Select("SELECT * FROM  client WHERE id = #{id}")
    Client findOne(@Param("id") Long id);

    @Select("SELECT * FROM client")
    List<Client> findAll();

    @Select("SELECT * FROM client WHERE clientId = #{clientId}")
    Client findByClientId(@Param("clientId") String clientId);

    @Select("SELECT * FROM client WHERE clientSecret = #{secret}")
    List<Client> findByClientSecret(@Param("secret") String secret);
}
