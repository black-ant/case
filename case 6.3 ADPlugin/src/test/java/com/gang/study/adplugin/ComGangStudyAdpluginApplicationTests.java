package com.gang.study.adplugin;

import com.gang.study.adplugin.crud.OrgOutputImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComGangStudyAdpluginApplicationTests {

    @Autowired
    private OrgOutputImpl orgOutput;

    @Test
    void contextLoads() {
        orgOutput.init(null);
        orgOutput.create(null, null);
    }

}
