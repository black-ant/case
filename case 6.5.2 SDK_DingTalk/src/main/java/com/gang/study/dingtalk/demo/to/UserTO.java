package com.gang.study.dingtalk.demo.to;

import lombok.Data;

import java.util.List;

/**
 * @Classname UserTO
 * @Description TODO
 * @Date 2020/1/19 16:28
 * @Created by zengzg
 */
@Data
public class UserTO {

    private String id;
    private String userid;
    private String name;
    private String orderInDepts;
    private String position;
    private String mobile;
    private String tel;
    private String workPlace;
    private String remark;
    private String email;
    private String orgEmail;
    private String jobnumber;
    private boolean isHide;
    private boolean isSenior;
    private List<Integer> department;

}
