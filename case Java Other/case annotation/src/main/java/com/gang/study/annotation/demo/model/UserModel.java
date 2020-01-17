package com.gang.study.annotation.demo.model;

import com.gang.study.annotation.demo.annotation.UserAnno;
import com.gang.study.annotation.demo.annotation.UserClassAnno;
import lombok.Data;

@Data
@UserClassAnno(value = "ant-black-class")
public class UserModel {

    @UserAnno(value = "ant-black")
    private String name;
}
