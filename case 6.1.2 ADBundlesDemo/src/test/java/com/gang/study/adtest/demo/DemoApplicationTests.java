package com.gang.study.adtest.demo;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.adtest.demo.output.GroupOutpulImpl;
import com.gang.study.adtest.demo.output.OrgOutputImpl;
import com.gang.study.adtest.demo.output.UserOutputImpl;
import com.gang.study.adtest.demo.to.ADConfigInfoTO;
import com.gang.study.adtest.demo.to.ADGroupTO;
import com.gang.study.adtest.demo.to.ADOrgTO;
import com.gang.study.adtest.demo.to.ADUserTO;
import com.gang.study.adtest.demo.to.DeleteInfo;
import com.gang.study.adtest.demo.to.SearchTO;
import com.gang.study.adtest.demo.utils.ADSyncUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgOutputImpl orgOutput;

    @Autowired
    private UserOutputImpl userOutput;

    @Autowired
    private GroupOutpulImpl groupOutpul;

    @Test
    public void contextLoads() {

        testOrg();

        //        testGroup();

        //        testUser();
        //        try {
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
    }

    public void testOrg() {
        //        orgOutput.init(getConfigTO());

        ADOrgTO adOrgTO = orgOutput.create(createOrgTO(), getConfigTO());

        //       ADOrgTO adOrgTO =  orgOutput.update(null, null)

        //        orgOutput.delete(null, null);

        //        orgOutput.search(getSearchInfo(), null);

        //        orgOutput.get(getSearchInfo(), null);

        //        orgOutput.search();


        logger.info("------> this is back key  :{} <-------", adOrgTO.getKey());
        logger.info("------> this is back TO :{} <-------", JSONObject.toJSONString(adOrgTO));
    }

    public void testGroup() {
        //        groupOutpul.init(getConfigTO());

        ADGroupTO adGroupTO = groupOutpul.create(creatGroupTO(), getConfigTO());

        //        ADGroupTO adGroupTO = groupOutpul.create(creatGroupTO(), getConfigTO());

        //        groupOutpul.search(getSearchInfo(), null);


        //        groupOutpul.delete(null, null);

        logger.info("------> this is back key  :{} <-------", adGroupTO.getKey());
        logger.info("------> this is back TO :{} <-------", JSONObject.toJSONString(adGroupTO));
    }

    public void testUser() {
        //        userOutput.init(getConfigTO());

        //        ADUserTO adUserTO = userOutput.create(creatUserTO(), getConfigTO());

        //        ADUserTO adUserTO = userOutput.search(getSearchInfo(), null);

        ADUserTO adUserTO = adUserTO = userOutput.update(updateUserTO(), getConfigTO());

        //            ADUserTO adUserTO = adUserTO = userOutput.delete(getDeleteInfo(), null);

        logger.info("------> this is back key  :{} <-------", adUserTO.getKey());
        logger.info("------> this is back TO :{} <-------", adUserTO);

    }


    public ADConfigInfoTO getConfigTO() {
        ADConfigInfoTO adConfigInfoTO = new ADConfigInfoTO();
        adConfigInfoTO.setHost("127.0.0.1");
        //        adConfigInfoTO.setPort("43891");
        adConfigInfoTO.setPort("46361");
        adConfigInfoTO.setAccount("administrator@wdhacpoc");
        adConfigInfoTO.setPassword("19950824");
        adConfigInfoTO.setBaseContext("DC=wdhacpoc,DC=com,DC=cn");
        adConfigInfoTO.setIsSSL(Boolean.TRUE);
        return adConfigInfoTO;
    }

    /**
     * b66e674d-4158-43b1-8d7c-e5f5820f73d8  31name
     *
     * @return
     */
    public ADOrgTO createOrgTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发0218-");
        ADOrgTO adOrgTO = new ADOrgTO();
        adOrgTO.setName(nameString + "name");
        adOrgTO.setCn(nameString + "cn");
        adOrgTO.setDescription("test");
        adOrgTO.setOrg("");
        //        adOrgTO.setOrg("44233412-7e89-4914-b82d-e1144fc33cae");
        return adOrgTO;
    }

    public ADOrgTO updateOrgTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发");
        ADOrgTO adOrgTO = new ADOrgTO();
        adOrgTO.setName(nameString);
        adOrgTO.setOrg("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        return adOrgTO;
    }

    /**
     * 10bddaf5-8be8-4aae-b2a1-5c5020e18448  157
     *
     * @return
     */
    public ADGroupTO creatGroupTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发");
        ADGroupTO adGroupTO = new ADGroupTO();
        adGroupTO.setName(nameString);
        adGroupTO.setOrg("44233412-7e89-4914-b82d-e1144fc33cae");
        adGroupTO.setDisplayName(nameString);
        adGroupTO.setDescription("test");
        adGroupTO.setMail("11111");
        adGroupTO.setInfo(nameString + "info is info");
        adGroupTO.setSAMAccountName(nameString);
        adGroupTO.setCn(nameString);

        // 管理者  -- AD 中存在管理者概念 , 这个待确定

        return adGroupTO;
    }

    public ADGroupTO updateGroupTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发");
        ADGroupTO adGroupTO = new ADGroupTO();
        //        adGroupTO.setName(nameString);
        //        adGroupTO.setOrg("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        adGroupTO.setDisplayName(nameString);
        adGroupTO.setSAMAccountName(nameString);
        //        adGroupTO.setCn(nameString);

        return adGroupTO;
    }


    public ADUserTO creatUserTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "Sync");
        ADUserTO adUserTO = new ADUserTO();
        adUserTO.setName(nameString);
        adUserTO.setOrg("44233412-7e89-4914-b82d-e1144fc33cae");
        adUserTO.setDisplayName(nameString);
        adUserTO.setSnName("zeng");

        // --> ok
        adUserTO.setUsername(nameString);
        adUserTO.setUserPrincipalName(nameString);
        adUserTO.setSAMAccountName(nameString);
        adUserTO.setCn(nameString);
        adUserTO.setTitle(nameString);
        //        adUserTO.setIpPhone("互联网电话");
        //        adUserTO.setMobile(nameString);
        //        adUserTO.setStreetAddress(nameString);
        //        adUserTO.setInfo(nameString);
        //        adUserTO.setInitials("aaaa");
        //        adUserTO.setPostalCode("邮政编码");
        //        adUserTO.setPostOfficeBox("邮箱");
        //        adUserTO.setCity("武汉");
        //        adUserTO.setProvince("省");
        //        adUserTO.setMail("mail");
        //        adUserTO.setTelephoneNumber("联系电话");
        //        adUserTO.setPhysicalDeliveryOfficeName("办公室");
        //        adUserTO.setHomePhone("家庭电话");
        //        adUserTO.setCompany("公司");
        //        adUserTO.setDepartment("部门");
        //        adUserTO.setDescription("描述");
        //        adUserTO.setWWWHomePage("网页");

        //        adUserTO.setMemberOf(new String[]{
        //                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        //        });

        adUserTO.setLdapGroups(new String[]{
                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        });
        // --> not
        //        adGroupTO.setCountry("contry");

        //        adGroupTO.setGivename("gang");

        return adUserTO;
    }


    public ADUserTO updateUserTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "Sync") + "改";
        ADUserTO adGroupTO = new ADUserTO();
        //        adGroupTO.setName(nameString);
        //        adGroupTO.setOrg("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        //        adGroupTO.setDisplayName(nameString);
        //        adGroupTO.setUsername(nameString);
        //        adGroupTO.setUserPrincipalName(nameString);
        //        adGroupTO.setSAMAccountName(nameString);
        //        adGroupTO.setCn(nameString);
        adGroupTO.setKey("739b57b8-344c-4682-ac3a-b0791b4a5898");
        adGroupTO.setTitle(nameString);
        adGroupTO.setIpPhone(nameString);
        adGroupTO.setMobile(nameString);
        adGroupTO.setStreetAddress(nameString);
        adGroupTO.setLdapGroups(new String[]{
                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        });
        return adGroupTO;
    }

    public DeleteInfo<String, String> getDeleteInfo() {
        DeleteInfo<String, String> deleteInfo = new DeleteInfo<String, String>();
        deleteInfo.setDeleteid("dc92701b-ec66-4f44-a531-6dbc6e73993e");
        return deleteInfo;
    }

    public SearchTO<String, String> getSearchInfo() {
        SearchTO<String, String> searchTO = new SearchTO<String, String>();
        searchTO.setKey("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        searchTO.setParentId("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        return searchTO;
    }


}
