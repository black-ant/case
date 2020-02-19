package com.gang.study.adplugin;

import com.gang.study.adplugin.crud.GroupOutpulImpl;
import com.gang.study.adplugin.crud.OrgOutputImpl;
import com.gang.study.adplugin.crud.UserOutputImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComGangStudyAdpluginApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgOutputImpl orgOutput;

    @Autowired
    private UserOutputImpl userOutput;

    @Autowired
    private GroupOutpulImpl groupOutpul;

    @Test
    void contextLoads() {

        testOrg();

        testGroup();

        testUser();
    }

    public void testOrg() {
        orgOutput.init();
        logger.info("------> this is back :{} <-------", orgOutput.create(null));

        //        logger.info("------> this is back :{} <-------", orgOutput.update(null));

        //        orgOutput.delete(null);
    }

    public void testGroup() {
        //        groupOutpul.init();
        //        logger.info("------> this is back :{} <-------", groupOutpul.create(null));

        //        logger.info("------> this is back :{} <-------", groupOutpul.update(null));

        //        groupOutpul.delete(null);
    }

    public void testUser() {
        //        userOutput.init();
        //        logger.info("------> this is back :{} <-------", userOutput.create(null));

        //        logger.info("------> this is back :{} <-------", userOutput.update(null));

        //        userOutput.delete("");
    }

}
