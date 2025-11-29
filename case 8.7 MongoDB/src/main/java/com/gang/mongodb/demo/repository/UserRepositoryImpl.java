package com.gang.mongodb.demo.repository;

import com.gang.mongodb.demo.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname UserRepositoryImpl
 * @Description TODO
 * @Date 2021/9/21
 * @Created by zengzg
 */
@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     * @param user
     */
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    /**
     * 根据用户名查询对象
     *
     * @param userName
     * @return
     */
    public User findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }

    /**
     * 更新对象
     *
     * @param user
     */
    public long updateUser(User user) {
        Query query = new Query(Criteria.where("id").is(user.getSelfId()));
        Update update = new Update().set("userName", user.getUsername()).set("passWord", user.getPassword());
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
        if (result != null)
            return result.getMatchedCount();
        else
            return 0;
    }

    /**
     * 删除对象
     *
     * @param id
     */
    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, User.class);
    }
}
