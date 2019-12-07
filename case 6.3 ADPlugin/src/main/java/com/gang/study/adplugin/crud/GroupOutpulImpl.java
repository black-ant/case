package com.gang.study.adplugin.crud;

import com.gang.study.adplugin.common.ADConfig;
import com.gang.study.adplugin.utils.ADSyncUtils;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import net.tirasa.connid.bundles.ad.crud.ADDelete;
import net.tirasa.connid.bundles.ad.crud.ADUpdate;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import para.cic.sync.common.api.AbstractOrgOutputService;
import para.cic.sync.common.lib.to.AbstractConfigTO;
import para.cic.sync.common.lib.to.DeleteInfo;
import para.cic.sync.common.lib.to.SearchTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname GroupOutpulImpl
 * @Description TODO
 * @Date 2019/12/5 13:40
 * @Created by zengzg
 */
@Component
public class GroupOutpulImpl extends AbstractOrgOutputService<String, String> {

    @Autowired
    private ADConfig adConfig;

    private ADCreate adCreate;

    private ADUpdate adUpdate;

    private ADDelete adDelete;

    ObjectClass objectClass = ObjectClass.GROUP;

    @Override
    public void init(AbstractConfigTO abstractConfigTO) {
        test();
        adCreate = new ADCreate(adConfig.getAdConnection(), objectClass, getInfo(), null);
    }

    @Override
    public String checkAccessToken() {
        return null;
    }

    /*
    0cb795ce-ec67-4090-b248-f2008961c739  Sync563
     */
    @Override
    public String create(String s, AbstractConfigTO abstractConfigTO) {
        return adCreate.create().getUidValue();
    }

    @Override
    public String update(String s, AbstractConfigTO abstractConfigTO) {
        adUpdate = new ADUpdate(adConfig.getAdConnection(), objectClass, new Uid("0cb795ce-ec67-4090-b248-f2008961c739"));
        return adUpdate.update(getUpdateInfo()).getUidValue();
    }

    @Override
    public String delete(DeleteInfo deleteInfo, AbstractConfigTO abstractConfigTO) {
        adDelete = new ADDelete(adConfig.getAdConnection(), objectClass, new Uid("6579d0d8-c633-47ea-89ff-2307b0cc94d0"));
        adDelete.delete();
        return "delete ok";
    }

    @Override
    public AbstractConfigTO checkConfig() {
        return null;
    }

    @Override
    public String get(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    @Override
    public String list(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    @Override
    public String listChild(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }


    public void test() {
        adConfig.getAdConnection().test();
    }

    public Set<Attribute> getInfo() {
        // v 1.0 测试连接器正常访问
        Set<Attribute> attrs = new HashSet<Attribute>();
        objectClass = new ObjectClass("__GROUP__");
        //		attrs.add(new Name(dataCreateUtil("name", "Name")));

        // 逻辑判断数据
        //		attrs.add(AttributeBuilder.build("ldapGroups", "f24fbb50-20db-4765-bdbf-0f2845f6403c")); // 逻辑上会用于获取scheme

        // V 2.0 其他数据准备
        attrs.add(AttributeBuilder.build("__ORG__", "5a58a54e-caed-462e-b80e-23e57abf2dd5"));
        //        		attrs.add(AttributeBuilder.build("__ORG__", ""));

        String name = ADSyncUtils.dataCreateUtil("name", "Sync");
        //		String name = "Internet";
        attrs.add(AttributeBuilder.build("displayName", name));
        attrs.add(AttributeBuilder.build("sAMAccountName", name));
        attrs.add(AttributeBuilder.build("cn", name));
        //        attrs.add(AttributeBuilder.build("ldapGroups", ""));

        attrs.add(AttributeBuilder.build("__NAME__", name)); // 暂未用上
        return attrs;
    }

    public Set<Attribute> getUpdateInfo() {

        Set<Attribute> attrs = new HashSet<Attribute>();

        //        attrs.add(AttributeBuilder.build("__ORG__", "7bf9b2a7-b825-4b59-8eef-979afd0db745"));
        String name = ADSyncUtils.dataCreateUtil("name", "Sync");

        //        attrs.add(AttributeBuilder.build("cn", "CGP4931"));
        //        attrs.add(AttributeBuilder.build("name", "CGP4931"));
        attrs.add(AttributeBuilder.build("displayName", name));
        attrs.add(AttributeBuilder.build("description", name));

        return attrs;
    }
}
