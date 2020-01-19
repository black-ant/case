package com.gang.study.dingtalk.demo.to;

import lombok.Data;

/**
 * @Classname OrgTO
 * @Description TODO
 * @Date 2020/1/19 15:25
 * @Created by zengzg
 */
@Data
public class OrgTO {

    private String id;

    private String name;
    private String parentid;
    private String order;
    private boolean createDeptGroup;
    private boolean deptHiding;
    private String deptPermits;
    private String userPermits;
    private boolean outerDept;
    private String outerPermitDepts;
    private String outerPermitUsers;
    private String sourceIdentifier;

}
