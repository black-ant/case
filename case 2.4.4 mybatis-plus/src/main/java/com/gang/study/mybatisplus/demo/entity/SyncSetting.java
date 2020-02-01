package com.gang.study.mybatisplus.demo.entity;

import java.time.LocalDateTime;
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
public class SyncSetting extends AbstractEntity {

    private static final long serialVersionUID=1L;

    /**
     * 配置体
     */
    private String settingBody;

    /**
     * 配置名称
     */
    private String settingName;

    /**
     * 配置类型
     */
    private String settingType;

    private String settingTypeCode;

    /**
     * 配置控制
     */
    private String settingControl;

    /**
     * 配置策略
     */
    private String settingPolicy;

    private LocalDateTime createDate;


}
