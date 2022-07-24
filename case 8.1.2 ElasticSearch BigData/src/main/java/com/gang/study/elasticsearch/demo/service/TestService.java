package com.gang.study.elasticsearch.demo.service;

import java.util.Date;

import cn.hutool.core.util.HashUtil;
import com.gang.study.elasticsearch.demo.entity.AOrder;
import com.gang.study.elasticsearch.demo.entity.BOrder;
import com.gang.study.elasticsearch.demo.entity.COrder;
import com.gang.study.elasticsearch.demo.repository.AOrderRepository;
import com.gang.study.elasticsearch.demo.repository.BOrderRepository;
import com.gang.study.elasticsearch.demo.repository.COrderRepository;
import com.gang.study.elasticsearch.demo.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Service
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final List<Integer> status = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
    private static final List<Integer> sex = Arrays.asList(new Integer[]{0, 1});
    private static final List<String> role = Arrays.asList(new String[]{"Role_Admin", "Role_User", "Role_Employee", "Role_Account"});


    @Autowired
    private AOrderRepository aorderRepository;

    @Autowired
    private BOrderRepository bOrderRepository;

    @Autowired
    private COrderRepository cOrderRepository;

    public void add() {
        logger.info("测试创建");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(12);
        for (int i = 0; i < 30000; i++) {
            int item = i;
            executor.submit(() -> {
                AOrder order = createOrderA(item);
                aorderRepository.save(order);

                BOrder order2 = createOrderB(item);
                bOrderRepository.save(order2);

                COrder order3 = createOrderC(item);
                cOrderRepository.save(order3);

                logger.info("------> 创建完成 :{}<-------", item);
            });
        }
    }

    public AOrder createOrderA(int i) {
        MD5 md = new MD5();

        AOrder order = new AOrder();
        order.setUsername("AntBlack" + i);
        order.setType("A");
        order.setAge(new Random().nextInt(30));
        order.setAddress("武汉" + new Random().nextInt(99));
        order.setMobile("158" + new Random().nextInt(99999999));
        order.setEmail("111" + new Random().nextInt(99999) + "@qq.com");
        order.setPassword(md.start(String.valueOf(new Random().nextInt(9999))));
        order.setStatus(status.get(getRandom(status.size())));
        order.setIdCard(0L);
        order.setSex(sex.get(getRandom(sex.size())));
        order.setRole(role.get(getRandom(role.size())));
        return order;
    }

    public BOrder createOrderB(int i) {
        BOrder order = new BOrder();
        order.setUsername("AntBlack" + i);
        order.setType("B");
        order.setAge(new Random().nextInt(30));
        order.setInnerAddress("武汉" + new Random().nextInt(99));
        order.setUsername("AntBlack" + i);
        order.setCreateTime(new Date());
        order.setStatus(status.get(getRandom(status.size())));
        order.setSex(sex.get(getRandom(sex.size())));
        order.setRole(role.get(getRandom(role.size())));
        return order;
    }

    public COrder createOrderC(int i) {
        COrder order = new COrder();
        order.setUsername("AntBlack" + i);
        order.setAge(new Random().nextInt(30));
        order.setOutAddress("武汉" + new Random().nextInt(99));
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setExtension("武汉" + new Random().nextInt(99));
        order.setStatus(status.get(getRandom(status.size())));
        order.setSex(sex.get(getRandom(sex.size())));
        order.setType("C");
        return order;
    }


    public Integer getRandom(Integer size) {
        Random random = new Random();
        return random.nextInt(size);
    }
}
