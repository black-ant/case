package com.gang.study.hikari.demo.service;

import com.gang.study.hikari.demo.entity.UserEntity;
import com.gang.study.hikari.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务类
 * <p>
 * 演示使用 HikariCP 连接池的数据库操作。
 * HikariCP 会自动管理数据库连接的获取和释放。
 * </p>
 * 
 * <h3>连接池工作原理：</h3>
 * <ol>
 *     <li>应用启动时，连接池创建一定数量的连接</li>
 *     <li>请求到来时，从池中获取可用连接</li>
 *     <li>操作完成后，连接归还到池中复用</li>
 *     <li>空闲连接超时会被回收</li>
 * </ol>
 *
 * @author zengzg
 * @since 2019/4/28
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    public List<UserEntity> findUser() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    /**
     * 根据用户名查询
     *
     * @return 用户列表
     */
    public List<UserEntity> findByUsername() {
        logger.info("Finding users by username: gang");
        return userRepository.getByUserName("gang");
    }

    /**
     * 根据 ID 查询用户
     *
     * @param id 用户 ID
     * @return 用户对象（可能为空）
     */
    public Optional<UserEntity> findById(Integer id) {
        logger.info("Finding user by id: {}", id);
        return userRepository.findById(id);
    }

    /**
     * 保存用户（事务操作）
     * <p>
     * @Transactional 确保操作的原子性。
     * 如果发生异常，整个操作会回滚。
     * </p>
     *
     * @param user 用户对象
     * @return 保存后的用户
     */
    @Transactional
    public UserEntity save(UserEntity user) {
        logger.info("Saving user: {}", user.getUsername());
        return userRepository.save(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户 ID
     */
    @Transactional
    public void delete(Integer id) {
        logger.info("Deleting user by id: {}", id);
        userRepository.deleteById(id);
    }
}
