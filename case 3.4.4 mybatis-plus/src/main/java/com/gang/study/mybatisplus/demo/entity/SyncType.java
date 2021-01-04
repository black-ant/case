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
public class SyncType extends AbstractEntity {

    private static final long serialVersionUID=1L;

    private String typeCode;

    /**
     * 加载类
     */
    private String typeClass;

    /**
     * 策略类型
     */
    private String typePolicy;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 供应商类型
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 处理类型
     */
    private String dataType;


}
