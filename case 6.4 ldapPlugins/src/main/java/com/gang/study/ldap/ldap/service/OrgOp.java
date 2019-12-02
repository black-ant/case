package com.gang.study.ldap.ldap.service;

import com.gang.study.ldap.ldap.utils.LDAPUtils;
import com.sun.xml.internal.rngom.parse.host.Base;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.LdapConnector;
import net.tirasa.connid.bundles.ldap.modify.LdapCreate;
import net.tirasa.connid.bundles.ldap.modify.LdapDelete;
import net.tirasa.connid.bundles.ldap.modify.LdapUpdate;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Classname OrgOp
 * @Description TODO
 * @Date 2019/12/2 18:42
 * @Created by zengzg
 */
@Component
public class OrgOp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseHandle baseHandle;

    // --> 连接器
    private LdapConnector ldapConnector;
    private LdapConnection ldapConnection;


    private LdapCreate ldapCreate;
    private LdapUpdate ldapUpdate;
    private LdapDelete ldapDelete;

    public void init() {
        ldapConnector = new LdapConnector();
        ldapConnector.init(baseHandle.getConfiguration());
        ldapConnection = new LdapConnection(baseHandle.getConfiguration());

        // --> Test Connect
        testConnect();
    }

    public void testConnect() {
        try {
            ldapConnection.test();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }


    }

    public void createOrg() {
        init();
        ldapCreate = new LdapCreate(ldapConnection, ObjectClass.ACCOUNT, getUserInfo(), null);
        try {
            ldapCreate.execute();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }

    }

    public Set<Attribute> getUserInfo() {
        // v 1.0 测试连接器正常访问
        Set<Attribute> attrs = new HashSet<Attribute>();
        ObjectClass objectClass = new ObjectClass("__ACCOUNT__");
        attrs.add(new Name("U测试"));

        // 判断参数 , 用于逻辑判断
        String codeString = String.valueOf(new Random().nextInt(999999));

        attrs.add(AttributeBuilder.build("sAMAccountName", codeString));
        attrs.add(AttributeBuilder.build("userPrincipalName", codeString));
        //		attrs.add(AttributeBuilder.build("__NAME__", "666735"));
        attrs.add(AttributeBuilder.build("Title", "行长"));
        attrs.add(AttributeBuilder.build("ipPhone", "3"));
        attrs.add(AttributeBuilder.build("streetAddress", "大和")); // 可测试邮箱是否准备

        // end : ADCreate LOG -> in create executeImpl method

        // v 2.0 测试创建前数据准备
        //		String cnName = "CACN298";
        String cnName = LDAPUtils.dataCreateUtil("name", "CACN");
        attrs.add(AttributeBuilder.build("cn", cnName));
        attrs.add(AttributeBuilder.build("__ORG__", "703c0597-c412-4552-8a21-721eba30ed70"));
        attrs.add(AttributeBuilder.build("__PASSWORD__", new GuardedString("password".toCharArray())));
        attrs.add(AttributeBuilder.build("displayName", cnName));
        attrs.add(AttributeBuilder.build("description", cnName));
        attrs.add(AttributeBuilder.build("_serverIP", "127.0.0.2"));
        attrs.add(AttributeBuilder.build("info", "兼职"));
        //		attrs.add(AttributeBuilder.build("_serverip", "127.0.0.3"));
        attrs.add(AttributeBuilder.build("operationAuth", Boolean.TRUE));
        attrs.add(AttributeBuilder.build("physicalDeliveryOfficeName", "IT办公室一"));

        String[] serverArrays = {"71072553-9F6F-44B7-9C75-316958F7DA3D", "BFFC074B-14E2-4C9A-B563-389BD4E2D45C"};
        //		attrs.add(AttributeBuilder.build("memberOf", ));
        //		attrs.add(AttributeBuilder.build("streetAddress", "大和")); // 用于记录 ，区别于上一个
        attrs.add(AttributeBuilder.build("mail", "")); // 测试传入邮箱是否新建
        return attrs;
    }
}
