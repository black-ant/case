package com.mybatistest.demo.mapper;

import com.mybatistest.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 Mapper 接口
 * <p>
 * 演示 MyBatis 的两种 SQL 定义方式：
 * <ul>
 *     <li>XML 映射文件 - 适合复杂 SQL</li>
 *     <li>注解方式 - 适合简单 SQL</li>
 * </ul>
 * </p>
 * 
 * <h3>参数绑定方式：</h3>
 * <ul>
 *     <li>@Param - 命名参数</li>
 *     <li>#{param} - 预编译（防 SQL 注入）</li>
 *     <li>${param} - 直接拼接（谨慎使用）</li>
 * </ul>
 * 
 * <h3>XML 映射位置：</h3>
 * <p>resources/mapper/UserMapper.xml</p>
 *
 * @author zengzg
 * @since 1.0
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据主键删除用户
     * <p>
     * SQL 定义在 UserMapper.xml
     * </p>
     *
     * @param sn 用户序列号
     * @return 影响行数
     */
    int deleteByPrimaryKey(String sn);

    /**
     * 插入用户（全字段）
     *
     * @param record 用户对象
     * @return 影响行数
     */
    int insert(User record);

    /**
     * 选择性插入用户
     * <p>
     * 只插入指定的字段
     * </p>
     *
     * @param sn       序列号
     * @param username 用户名
     * @return 影响行数
     */
    int insertSelect(@Param("sn") String sn, @Param("username") String username);

    /**
     * 根据主键查询用户
     *
     * @param sn 用户序列号
     * @return 用户对象
     */
    User selectByPrimaryKey(String sn);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> selectAll();

    /**
     * 根据主键更新用户
     *
     * @param record 用户对象
     * @return 影响行数
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询（注解方式）
     * <p>
     * 使用 @Select 注解直接定义 SQL，适合简单查询。
     * </p>
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByFilter(String username);
}
