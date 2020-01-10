package com.gang.study.mybatisgeneratorgui.mybatisgeneratorgui.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * cic_msg_type
 * @author 
 */
@Table(name="cic_msg_type")
@Data
public class CicMsgType implements Serializable {
    @Id
    @GeneratedValue
    private String id;

    /**
     * 对应种类
     */
    private String msgType;

    /**
     * 对应供应商
     */
    private String supplierName;

    /**
     * 对应实现类
     */
    private String msgClass;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * msg 对外辨别名
     */
    private String displayName;

    private String domainId;

    /**
     * 权限属性
     */
    private String permission;

    /**
     * cfg配置策略type
     */
    private String connetPolicyType;

    /**
     * cfg配置策略type
     */
    private String checkPolicyType;

    /**
     * internet_user_type
     */
    private String userType;

    /**
     * 业务控制
     */
    private String control;

    private static final long serialVersionUID = 1L;
}