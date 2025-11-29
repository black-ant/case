package com.myjpa.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 用户实体类
 * <p>
 * 映射到数据库表 user_1，演示 JPA 实体的基本用法。
 * </p>
 * 
 * <h3>JPA 注解说明：</h3>
 * <ul>
 *     <li>@Entity - 标记为 JPA 实体</li>
 *     <li>@Table - 指定映射的数据库表名</li>
 *     <li>@Id - 主键字段</li>
 *     <li>@Column - 列映射配置</li>
 *     <li>@Basic - 基本属性映射</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@Entity
@Table(name = "user_1")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /**
     * 用户ID（主键）
     */
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer userid;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户类型
     */
    @Column(name = "usertype")
    private String usertype;

    /**
     * 是否激活 (1=激活, 0=未激活)
     */
    @Column(name = "isactive")
    private Byte isactive;

    /**
     * 用户关联链接
     */
    @Column(name = "userlink")
    private String userlink;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 所属组织ID
     */
    @Column(name = "orgid")
    private String orgid;
}
