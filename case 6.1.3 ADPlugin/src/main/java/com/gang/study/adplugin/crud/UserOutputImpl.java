package com.gang.study.adplugin.crud;

import com.gang.study.adplugin.common.ADConfig;
import com.gang.study.adplugin.utils.ADSyncUtils;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import net.tirasa.connid.bundles.ad.crud.ADDelete;
import net.tirasa.connid.bundles.ad.crud.ADUpdate;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Classname UserOutputImpl
 * @Description TODO
 * @Date 2019/12/5 13:40
 * @Created by zengzg
 */
@Component
public class UserOutputImpl {

    @Autowired
    private ADConfig adConfig;

    private ADCreate adCreate;

    private ADUpdate adUpdate;

    private ADDelete adDelete;

    ObjectClass objectClass = ObjectClass.ACCOUNT;

    public void init() {
        test();
        adCreate = new ADCreate(adConfig.getAdConnection(), objectClass, getInfo(), null);
    }

    public String checkAccessToken() {
        return null;
    }

    /*
        f084583a-22f3-48d0-a256-49a5e8ea0b4f  Sync250


     */
    public String create(String s) {
        Uid ui = adCreate.create();
        return ui.getUidValue();
    }

    public String update(String s) {
        adUpdate = new ADUpdate(adConfig.getAdConnection(), objectClass, new Uid("f084583a-22f3-48d0-a256-49a5e8ea0b4f"));
        return adUpdate.update(getUpdateInfo()).getUidValue();
    }

    public String delete(String deleteInfo) {
        adDelete = new ADDelete(adConfig.getAdConnection(), objectClass, new Uid("4b6df591-5a4b-4e5a-9889-c424f1656a87"));
        adDelete.delete();
        return "delete ok";
    }

    public void test() {
        adConfig.getAdConnection().test();
    }

    public Set<Attribute> getInfo() {
        // v 1.0 测试连接器正常访问

        Set<Attribute> attrs = new HashSet<Attribute>();

        // 判断参数 , 用于逻辑判断
        String codeString = String.valueOf(new Random().nextInt(999999));

        attrs.add(AttributeBuilder.build("sAMAccountName", codeString));
        attrs.add(AttributeBuilder.build("userPrincipalName", codeString));

        //        attrs.add(AttributeBuilder.build("Title", "行长"));
        //        attrs.add(AttributeBuilder.build("streetAddress", "大和")); // 可测试邮箱是否准备

        // end : ADCreate LOG -> in create executeImpl method

        // v 2.0 测试创建前数据准备
        //		String cnName = "CACN298";
        String cnName = ADSyncUtils.dataCreateUtil("name", "Sync");
        attrs.add(AttributeBuilder.build("cn", cnName));
        attrs.add(AttributeBuilder.build("name", cnName));
        attrs.add(new Name(cnName));
        attrs.add(AttributeBuilder.build("__ORG__", "5a58a54e-caed-462e-b80e-23e57abf2dd5"));
        //        attrs.add(AttributeBuilder.build("__PASSWORD__", new GuardedString("password".toCharArray())));
        //        attrs.add(AttributeBuilder.build("displayName", cnName));
        //        attrs.add(AttributeBuilder.build("description", cnName));
        //        attrs.add(AttributeBuilder.build("physicalDeliveryOfficeName", "IT办公室一"));

        //        String[] serverArrays = {"71072553-9F6F-44B7-9C75-316958F7DA3D", "BFFC074B-14E2-4C9A-B563-389BD4E2D45C"};
        //		attrs.add(AttributeBuilder.build("memberOf", ));
        //		attrs.add(AttributeBuilder.build("streetAddress", "大和")); // 用于记录 ，区别于上一个
        //        attrs.add(AttributeBuilder.build("mail", "")); // 测试传入邮箱是否新建
        return attrs;
    }

    public Set<Attribute> getUpdateInfo() {

        Set<Attribute> attrs = new HashSet<Attribute>();

        // 判断参数 , 用于逻辑判断 (不可为空)
        attrs.add(AttributeBuilder.build("sAMAccountName", "992427"));
        //		attrs.add(AttributeBuilder.build("userPrincipalName", "CACN266"));
        attrs.add(AttributeBuilder.build("title", "2改"));
        attrs.add(AttributeBuilder.build("mobile", "11112改"));
        attrs.add(AttributeBuilder.build("ipPhone", "5"));
        //		attrs.add(AttributeBuilder.build("__PASSWORD__", new GuardedString("psaasow@123".toCharArray())));
        //		attrs.add(AttributeBuilder.build("userAccountControl", "514"));
        attrs.add(AttributeBuilder.build("streetAddress", "大和2改")); // 可测试邮箱是否准备
        // end : ADCreate LOG -> in create executeImpl method

        //		 v 2.0 测试创建前数据准备
        //        attrs.add(AttributeBuilder.build("__ORG__", "b3766519-98cb-47a2-bb07-0ad90273b551"));
        // "38B66684-D496-4DE2-9F77-B981D12227B9"
        //        String[] serverArrays = {"71072553-9F6F-44B7-9C75-316958F7DA3D",};
        //        attrs.add(AttributeBuilder.build("memberOf", serverArrays));
        attrs.add(AttributeBuilder.build("physicalDeliveryOfficeName", "IT办公室一该"));
        return attrs;
    }
}
