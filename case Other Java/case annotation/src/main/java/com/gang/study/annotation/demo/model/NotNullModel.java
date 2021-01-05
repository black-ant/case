package com.gang.study.annotation.demo.model;

import com.gang.study.annotation.demo.annotation.AssociationNotNull;
import lombok.Data;

@Data
public class NotNullModel {

    @AssociationNotNull(message = "测试自定义")
    private String name;

}
