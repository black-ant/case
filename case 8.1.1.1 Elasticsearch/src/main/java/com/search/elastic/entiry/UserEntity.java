package com.search.elastic.entiry;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Classname Sample
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
@Document(indexName = "user", type = "sync")
public class UserEntity {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private String id;

    @Field(type = FieldType.Text, store = true)
    private String userName;

    @Field(type = FieldType.Text, store = true)
    private String userInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
