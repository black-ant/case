package com.security.demo.service.security;

import com.security.demo.entity.UserSummaryEntity;
import com.security.demo.repository.UserSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/17 22:46
 * @Version 1.0
 **/
@Service
public class JPAUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserSummaryRepository userSummaryRepository;

    /**
     * 根据username 查找账户信息 并返回用户信息实体
     * @param username 用户名
     * @return 用于身份认证的 UserDetails 用户信息实体
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSummaryEntity user = userSummaryRepository.findByUsername(username);
        logger.debug("step1====: in UserDetailService :{}", user.toString());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
