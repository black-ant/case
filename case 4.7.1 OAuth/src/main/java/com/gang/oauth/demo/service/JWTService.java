package com.gang.oauth.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @Classname JWTService
 * @Description TODO
 * @Date 2021/2/3 23:33
 * @Created by zengzg
 */
public class JWTService {

    public void test(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
    }
}
