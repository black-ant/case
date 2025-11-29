package com.myjpa.demo.repository;

import com.myjpa.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户数据访问接口
 * <p>
 * 继承 JpaRepository 获得基本的 CRUD 操作，同时演示自定义查询。
 * </p>
 * 
 * <h3>Spring Data JPA 查询方式：</h3>
 * <ol>
 *     <li>方法名派生查询 - findByUsername(String username)</li>
 *     <li>@Query JPQL 查询 - 使用实体名和属性名</li>
 *     <li>@Query 原生 SQL - nativeQuery = true</li>
 *     <li>命名参数 - @Param("name")</li>
 *     <li>位置参数 - ?1, ?2</li>
 * </ol>
 * 
 * <h3>JpaRepository 提供的方法：</h3>
 * <ul>
 *     <li>save() - 保存/更新实体</li>
 *     <li>findById() - 根据 ID 查询</li>
 *     <li>findAll() - 查询所有</li>
 *     <li>delete() - 删除实体</li>
 *     <li>count() - 统计数量</li>
 * </ul>
 *
 * @author zengzg
 * @since 2019/4/28
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * 使用命名参数查询
     * <p>
     * JPQL 语法：使用实体类名和属性名，而非表名和列名
     * </p>
     *
     * @param username 用户名
     * @return 用户列表
     */
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    List<UserEntity> getByUserName(@Param("username") String username);

    /**
     * 使用位置参数更新
     * <p>
     * @Modifying 表示这是一个修改操作，需要配合 @Transactional 使用
     * </p>
     *
     * @param usertype 用户类型
     * @param userid   用户ID
     * @return 影响的行数
     */
    @Modifying
    @Query("UPDATE UserEntity u SET u.usertype = ?1 WHERE u.userid = ?2")
    Integer updateUser(String usertype, Integer userid);

    /**
     * 使用位置参数查询
     *
     * @param username 用户名
     * @return 用户列表
     */
    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    List<UserEntity> getByUserName1(String username);

    /**
     * 多表关联查询
     * <p>
     * 使用 JPQL 进行隐式关联查询
     * </p>
     *
     * @return 有组织的用户列表
     */
    @Query("SELECT u FROM UserEntity u, OrgEntity o WHERE u.orgid = o.id")
    List<UserEntity> getUserHaveOrg();
}
