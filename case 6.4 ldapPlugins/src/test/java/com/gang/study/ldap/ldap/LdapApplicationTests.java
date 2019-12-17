package com.gang.study.ldap.ldap;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.ldap.ldap.service.GroupOutpulImpl;
import com.gang.study.ldap.ldap.service.OrgOutputImpl;
import com.gang.study.ldap.ldap.service.UserOutputImpl;
import com.gang.study.ldap.ldap.to.ADBaseTO;
import com.gang.study.ldap.ldap.to.ADConfigInfoTO;
import com.gang.study.ldap.ldap.to.ADGroupTO;
import com.gang.study.ldap.ldap.to.ADOrgTO;
import com.gang.study.ldap.ldap.to.ADUserTO;
import com.gang.study.ldap.ldap.to.SearchTO;
import com.gang.study.ldap.ldap.utils.ADSyncUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class LdapApplicationTests {

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

        testGroup();

        testUser();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 110b3994-b0f9-1039-9626-b39b22fadbd2
     */
    public void testOrg() {
        //        ADOrgTO adOrgTO = orgOutput.create(createOrgTO(), getConfigTO());

        //        ADOrgTO adOrgTO = orgOutput.update(updateOrgTO(), getConfigTO());

        //        orgOutput.delete(null, null);

        orgOutput.search(getSearchInfo(), getConfigTO());

        //        orgOutput.get(getSearchInfo(), null);

        //        orgOutput.search();

        //        logger.info("------> this is back key  :{} <-------", adOrgTO.getKey());
        //        logger.info("------> this is back TO :{} <-------", JSONObject.toJSONString(adOrgTO));
    }

    public void testGroup() {

        //        ADGroupTO adGroupTO = groupOutpul.create(creatGroupTO(), getConfigTO());

        //        ADGroupTO adGroupTO = groupOutpul.create(creatGroupTO(), getConfigTO());

        groupOutpul.search(getSearchInfo(), getConfigTO());


        //        groupOutpul.delete(null, null);
        //
        //        logger.info("------> this is back key  :{} <-------", adGroupTO.getKey());
        //        logger.info("------> this is back TO :{} <-------", JSONObject.toJSONString(adGroupTO));
    }

    public void testUser() {

        //        ADUserTO adUserTO = userOutput.create(creatUserTO(), getConfigTO());

        List<ADBaseTO> adUserTO = userOutput.search(getSearchInfo(), getConfigTO());

        //        ADUserTO adUserTO = adUserTO = userOutput.update(updateUserTO(), getConfigTO());

        //        ADUserTO adUserTO = userOutput.delete("e170be94-b101-1039-962a-b39b22fadbd2", getConfigTO());

        //        logger.info("------> this is back key  :{} <-------", adUserTO.getKey());
        //        logger.info("------> this is back TO :{} <-------", adUserTO);

    }

    /**
     * baseContextToSynchronize=cn=root,dc=test,dc=com
     * host=127.0.0.1
     * port=6389
     * principal=cn=root,dc=test,dc=com
     * credentials=123456
     *
     * @return
     */
    public ADConfigInfoTO getConfigTO() {
        ADConfigInfoTO adConfigInfoTO = new ADConfigInfoTO();
        adConfigInfoTO.setHost("127.0.0.1");
        //        adConfigInfoTO.setPort("43891");
        adConfigInfoTO.setPort("6389");
        adConfigInfoTO.setAccount("cn=root,dc=test,dc=com");
        adConfigInfoTO.setPassword("123456");
        adConfigInfoTO.setBaseContext("cn=root,dc=test,dc=com");
        adConfigInfoTO.setIsSSL(Boolean.FALSE);
        return adConfigInfoTO;
    }

    /**
     * b66e674d-4158-43b1-8d7c-e5f5820f73d8  31name
     *
     * @return
     */
    public ADOrgTO createOrgTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发1206-");
        ADOrgTO adOrgTO = new ADOrgTO();
        adOrgTO.setName(nameString + "name");
        //        adOrgTO.setCn(nameString + "cn");
        adOrgTO.setDescription("test");
        //                adOrgTO.setOrg("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        adOrgTO.setOrg("");
        return adOrgTO;
    }

    public ADOrgTO updateOrgTO() {

        String nameString = ADSyncUtils.dataCreateUtil("name", "武汉研发");
        ADOrgTO adOrgTO = new ADOrgTO();
        adOrgTO.setName(nameString);
        adOrgTO.setKey("110b3994-b0f9-1039-9626-b39b22fadbd2");
        adOrgTO.setDescription("test123456");
        //        adOrgTO.setOrg("110b3994-b0f9-1039-9626-b39b22fadbd2");
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
        //        adGroupTO.setOrg("44233412-7e89-4914-b82d-e1144fc33cae");
        //        adGroupTO.setDisplayName(nameString);
        adGroupTO.setDescription("test");
        //        adGroupTO.setMail("11111");
        //        adGroupTO.setInfo(nameString + "info is info");
        //        adGroupTO.setSAMAccountName(nameString);
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
        //        adUserTO.setOrg("44233412-7e89-4914-b82d-e1144fc33cae");
        adUserTO.setDisplayName(nameString);
        adUserTO.setSnName("zeng");


        adUserTO.setCn(nameString);
        adUserTO.setTitle(nameString);
        adUserTO.setMobile(nameString);
        adUserTO.setStreetAddress(nameString);

        adUserTO.setInitials("aaaa");
        adUserTO.setPostalCode("邮政编码");
        adUserTO.setMail("mail");
        adUserTO.setTelephoneNumber("123456789");
        adUserTO.setPhysicalDeliveryOfficeName("办公室");
        adUserTO.setHomePhone("123456789");

        adUserTO.setDescription("描述");
        // --> ok
        //        adUserTO.setInfo(nameString);
        //        adUserTO.setCompany("公司");
        //        adUserTO.setDepartment("部门");
        //        adUserTO.setUsername(nameString);
        //        adUserTO.setUserPrincipalName(nameString);
        //        adUserTO.setSAMAccountName(nameString);
        //        adUserTO.setWWWHomePage("网页");

        //        adUserTO.setMemberOf(new String[]{
        //                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        //        });

        //        adUserTO.setLdapGroups(new String[]{
        //                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        //        });
        // --> not
        //        adGroupTO.setCountry("contry");

        //        adUserTO.setGivename("111");

        return adUserTO;
    }

    /**
     * Sync405
     *
     * @return
     */
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
        adGroupTO.setKey("e170be94-b101-1039-962a-b39b22fadbd2");
        adGroupTO.setTitle(nameString);
        adGroupTO.setMobile("1232145");
        adGroupTO.setStreetAddress(nameString);
        //        adGroupTO.setLdapGroups(new String[]{
        //                "10bddaf5-8be8-4aae-b2a1-5c5020e18448"
        //        });
        return adGroupTO;
    }

    public SearchTO<String, String> getSearchInfo() {
        SearchTO<String, String> searchTO = new SearchTO<String, String>();
        //        searchTO.setKey("5a58a54e-caed-462e-b80e-23e57abf2dd5");
        searchTO.setParentId("5612b0ae-aeaf-1039-992e-592c3f64a295");
        return searchTO;
    }

}