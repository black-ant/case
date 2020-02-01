package com.gang.study.mybatisplus.demo.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class ExpTemplate extends AbstractEntity {

    private static final long serialVersionUID=1L;

    /**
     * 模板名称
     */
    private String templateTitle;

    /**
     * 模板主体
     */
    private String templateBody;

    /**
     * 模板描述
     */
    private String templateDesc;

    private String templateModule;

    /**
     * 模板类型
     */
    private String templateType;

    /**
     * 模板创建人
     */
    private Integer createId;

    /**
     * 最后更新人
     */
    private Integer updateId;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField("update_Date")
    private LocalDateTime updateDate;


}
