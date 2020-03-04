package com.gang.study.adsource.demo.logic;

import com.gang.study.adsource.demo.utils.ADSyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/2/19 14:06
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrgLogic orgLogic;

    @Autowired
    private GroupLogic groupLogic;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private RangeSearch rangeSearch;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testOrg();
        //        testGroup();
        //        testUser();
    }

    public void testOrg() {
        try {
            orgLogic.init();

            //            logger.info("------> this is in create <-------");
            //            orgLogic.createOrg("gang0219");

            //            logger.info("------> this is in update <-------");
            //            orgLogic.update("gang0219", "gang021901");

            //            logger.info("------> this is in delete <-------");
            //            orgLogic.delete("gang021901");

            //            logger.info("------> this is search <-------");
            //            orgLogic.search("武汉研发1206-13", "上海派拉技术有限公司");

            testPage();

            //            byte[] cookie = null;
            //            cookie = orgLogic.searchPage(3, 9, "武汉研发194", cookie);
            //            cookie = orgLogic.searchPage(3, 9, "武汉研发194", cookie);
            //            cookie = orgLogic.searchPage(3, 9, "武汉研发194", cookie);

            //            orgLogic.searchList("武汉研发194");

        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    public void testPage() throws NamingException {
        rangeSearch.search("武汉研发194", 0, 5);
    }

    public void testGroup() {
        try {
            logger.info("------> this is in create <-------");
            groupLogic.create("group0219");

        } catch (NamingException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    public void testUser() {
        try {

            String userid = ADSyncUtils.dataCreateUtil("name", "user");

            logger.info("------> this is in create <-------");
            //            userLogic.create(userid);

            logger.info("------> this is in update <-------");
            userLogic.update("user578");

            logger.info("------> this is in delete <-------");
            //            orgLogic.delete("gang021901");

        } catch (NamingException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }


}
