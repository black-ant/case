package com.gang.study.mybatisplus.demo.entity;

import com.gang.study.mybatisplus.demo.to.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ant-black
 * @since 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SyncFieldInfo extends AbstractEntity {

    private static final long serialVersionUID=1L;

    /**
     * 资源字段
     */
    private String filedOrigin;

    private String fieldSource;

    private String fieldType;

    private String createUser;

    private String version;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 数据;类型
     */
    private String dataType;

    private String status;

    /**
     * 用户id
     */
    private String domainId;

    /**
     * 同步策略
     */
    private String policyType;

    /**
     * 同步类型
     */
    private String syncType;


}
