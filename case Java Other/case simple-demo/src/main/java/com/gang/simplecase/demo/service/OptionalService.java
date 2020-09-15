package com.gang.simplecase.demo.service;

import com.gang.simplecase.demo.service.to.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Classname OptionalService
 * @Description TODO
 * @Date 2020/9/15 11:02
 * @Created by zengzg
 */
@Component
public class OptionalService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        emptyOptional();
        //        ofOptional();
        //        ofNullableOptional();
        //        isPresentOptional();
        //        orElseOptional();
        //        ofElseGetOptional();
        //        orElseThrowOptional();
        //        exchangeOptional();
    }


    /**
     * empty 会抛出 NoSuchElementException
     */
    public void emptyOptional() {
        Optional<User> emptyOpt = Optional.empty();
        User user0 = emptyOpt.get();
        logger.info("------> this is user :{} <-------", user0);
    }

    /**
     * Of 中传入 null 会抛出 NullPointException
     */
    public void ofOptional() {

        Optional<User> user1 = Optional.of(new User("gang"));
        logger.info("------> this is user1 :{} <-------", user1);

        Optional<User> user2 = Optional.of(null);
        logger.info("------> this is user2 :{} <-------", user2);
    }

    /**
     * 传入 null 不会抛出异常
     */
    public void ofNullableOptional() {

        Optional<User> user1 = Optional.ofNullable(new User("gang"));
        logger.info("------> this is user1 :{} <-------", user1);

        Optional<User> user2 = Optional.ofNullable(null);
        logger.info("------> this is user2 :{} <-------", user2);
    }

    /**
     * 空类型处理 ,
     * 不论是哪种方式 , null 值在 Get 时均会有异常
     */
    public void isPresentOptional() {
        User user = new User("gang");
        Optional<User> opt = Optional.ofNullable(user);
        logger.info("------> this is isPresentOptional :{} <-------", opt.isPresent());
        logger.info("------> user :{} , equals : {} <-------", user.getUsername(), opt.get().getUsername());

        Optional<User> opt2 = Optional.ofNullable(null);
        logger.info("------> this is isPresentOptional2 :{} <-------", opt2.isPresent());
        logger.info("------> user :{} , equals : {} <-------", user.getUsername(), opt2.get().getUsername());
    }

    /**
     * orElse 来返回默认值
     */
    public void orElseOptional() {
        List<User> users = new ArrayList<>();
        User user = users.stream().findFirst().orElse(new User("default"));
        logger.info("------> user :{} , equals : {} <-------", user.getUsername(), "default".equals(user.getUsername()));
    }

    public void linkOptional() {
        User user = new User("1234");

        //        String result = Optional.ofNullable(user)
        //                .flatMap(u -> u.getAddress())
        //                .flatMap(a -> a.getCountry())
        //                .map(c -> c.getIsocode())
        //                .orElse("default");
    }

    public void ofElseGetOptional() {
        User user = null;
        logger.info("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        logger.info("------> this is result :{} <-------", result);

        logger.info("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        logger.info("------> this is result2 :{} <-------", result2);

        User user2 = new User("1234");
        logger.info("Using orElse");
        User result3 = Optional.ofNullable(user2).orElse(createNewUser());
        logger.info("------> this is result3 :{} <-------", result3);
        logger.info("Using orElseGet");
        // !!! 此时不会调用 CreateNewUser
        User result24 = Optional.ofNullable(user2).orElseGet(() -> createNewUser());
        logger.info("------> this is result24 :{} <-------", result24);


    }

    private User createNewUser() {
        logger.info("Creating New User");
        return new User("1234");
    }

    /**
     * 返回异常
     */
    public void orElseThrowOptional() {
        User user = new User("gang");
        User result = Optional.ofNullable(user)
                .orElseThrow(() -> new IllegalArgumentException());
        logger.info("------> this is result3 :{} <-------", result);

        User user2 = null;
        User result2 = Optional.ofNullable(user2)
                .orElseThrow(() -> new IllegalArgumentException());
        logger.info("------> this is result3 :{} <-------", result2);
    }

    /**
     *
     */
    public void exchangeOptional() {
        User user = new User("1234");
        String email = Optional.ofNullable(user)
                .map(u -> u.getAge()).orElse("default@gmail.com");

        logger.info("------> this is email :{} <-------", email);
    }

    public void nullOptional() {
        Optional<User> emptyOpt = null;
    }
}
