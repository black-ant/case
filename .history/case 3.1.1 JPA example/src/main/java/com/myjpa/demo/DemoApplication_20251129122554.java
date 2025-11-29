package com.myjpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Data JPA 示例应用
 * <p>
 * 本项目演示 Spring Data JPA 的核心功能：
 * <ul>
 *     <li>Entity 实体映射</li>
 *     <li>Repository 数据访问层</li>
 *     <li>JPQL 查询语言</li>
 *     <li>事务管理</li>
 *     <li>关联关系映射</li>
 * </ul>
 * </p>
 * 
 * <h3>JPA 核心注解：</h3>
 * <ul>
 *     <li>@Entity - 标记实体类</li>
 *     <li>@Table - 指定表名</li>
 *     <li>@Id - 主键</li>
 *     <li>@Column - 列映射</li>
 *     <li>@OneToMany/@ManyToOne - 关联关系</li>
 * </ul>
 * 
 * <h3>前置条件：</h3>
 * <p>需要配置 MySQL 数据库连接</p>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
