//package com.gang.ext.sdk.workwechat;
//
//import com.alibaba.fastjson.JSONObject;
//import com.gang.ext.sdk.workwechat.logic.BaseLogic;
//import com.gang.ext.sdk.workwechat.logic.OrgLogic;
//import com.gang.ext.sdk.workwechat.to.OrgTO;
//import com.gang.ext.sdk.workwechat.to.WorkWechatConfig;
//import com.gang.sdk.api.to.SyncConfig;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @Classname WorkWechatApplicationTest
// * @Description TODO
// * @Date 2019/12/28 12:16
// * @Created by zengzg
// */
//@SpringBootTest
//public class WorkWechatApplicationTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private BaseLogic baseLogic;
//
//    @Autowired
//    private OrgLogic orgLogic;
//
//    @Test
//    public void getToken() {
//        //        logger.info("------> {} <-------", baseLogic.getToken(getConfig()));
//        testCreate();
//        //        testUpdate();
//    }
//
//    public void testCreate() {
//        orgLogic.create(getOrg(), getConfig());
//    }
//
//    public void testUpdate() {
//        orgLogic.update(getUpdateOrg(), getConfig());
//    }
//
//    public String getConfigString() {
//        return JSONObject.toJSONString(getConfig());
//    }
//
//    public WorkWechatConfig getConfig() {
//        SyncConfig syncConfig = new WorkWechatConfig();
//        syncConfig.setAppId("ww72f872df46c2b36f");
//        syncConfig.setAppSecret("UqY3axjUkhzqd37VNUrsO9BeMXizZksMhCHZVm32dZs");
//        return (WorkWechatConfig) syncConfig;
//    }
//
//
//    public OrgTO getOrg() {
//        OrgTO orgTO = new OrgTO();
//        orgTO.setName("测试租户1");
//        orgTO.setName_en("test1");
//        orgTO.setParentid(2);
//        return orgTO;
//    }
//
//    public OrgTO getUpdateOrg() {
//        OrgTO orgTO = new OrgTO();
//        orgTO.setId("3");
//        orgTO.setName("测试租户111");
//        orgTO.setName_en("test111");
//        orgTO.setParentid(2);
//        return orgTO;
//    }
//}
