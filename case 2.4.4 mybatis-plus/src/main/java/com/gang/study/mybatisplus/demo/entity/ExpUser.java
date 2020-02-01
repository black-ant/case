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
public class ExpUser extends AbstractEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    private String username;

    private String userPower;

    private String userPassword;

    private String userType;


}
