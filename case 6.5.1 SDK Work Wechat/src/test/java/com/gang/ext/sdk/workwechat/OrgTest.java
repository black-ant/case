//package com.gang.ext.sdk.workwechat;
//
//import com.gang.common.lib.utils.IDUtils;
//import com.gang.ext.sdk.workwechat.logic.UserLogic;
//import com.gang.ext.sdk.workwechat.to.UserTO;
//import com.gang.ext.sdk.workwechat.to.WorkWechatConfig;
//import com.gang.sdk.api.to.SyncConfig;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @Classname OrgTest
// * @Description TODO
// * @Date 2020/1/12 20:54
// * @Created by zengzg
// */
////@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OrgTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private UserLogic userLogic;
//
//    @Test
//    public void test() {
//        //        create();
//        update("616274");
//    }
//
//    public void create() {
//        logger.info("------> {} <-------", userLogic.create(cteateTO(), createConfig()));
//    }
//
//    public void update(String key) {
//        logger.info("------> {} <-------", userLogic.update(updateTO(key), createConfig()));
//    }
//
//    public UserTO cteateTO() {
//        UserTO user = new UserTO();
//        user.setUserid(IDUtils.crateId());
//        user.setName("gang" + IDUtils.crateId(99));
//        user.setAaa("aaa" + IDUtils.crateId(99));
//        user.setAddress("武汉");
//        user.setEmail(IDUtils.createEmail());
//        user.setMobile(IDUtils.createMobil());
//        user.setDepartment(new String[]{"1"});
//        return user;
//    }
//
//    public UserTO updateTO(String id) {
//        UserTO user = new UserTO();
//        user.setUserid(id);
//        user.setName("gang1" + IDUtils.crateId(99) + "改");
//        user.setAaa("aaa1" + IDUtils.crateId(99) + "改");
//        user.setAddress("武汉1" + IDUtils.crateId(99) + "改");
//        user.setEmail(IDUtils.createEmail());
//        user.setMobile(IDUtils.createMobil());
//        user.setDepartment(new String[]{"4"});
//        return user;
//    }
//
//    public WorkWechatConfig createConfig() {
//        SyncConfig syncConfig = new WorkWechatConfig();
//        syncConfig.setAppId("ww72f872df46c2b36f");
//        syncConfig.setAppSecret("UqY3axjUkhzqd37VNUrsO9BeMXizZksMhCHZVm32dZs");
//        return (WorkWechatConfig) syncConfig;
//    }
//
//
//}
