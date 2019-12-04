package com.gang.study.adplugin.crud;


import com.gang.study.adplugin.common.ADConfig;
import com.gang.study.adplugin.utils.ADSyncUtils;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import para.cic.sync.common.api.AbstractOrgOutputService;
import para.cic.sync.common.lib.to.AbstractConfigTO;
import para.cic.sync.common.lib.to.DeleteInfo;
import para.cic.sync.common.lib.to.SearchTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname ADCreate
 * @Description TODO
 * @Date 2019/12/2 18:04
 * @Created by zengzg
 */
@Component
public class OrgOutputImpl extends AbstractOrgOutputService<String, String> {

    @Autowired
    private ADConfig adConfig;

    private ADCreate adCreate;

    ObjectClass objectClass = new ObjectClass("__ORGANIZATION__");

    @Override
    public void init(AbstractConfigTO abstractConfigTO) {
        test();
        adCreate = new ADCreate(adConfig.getAdConnection(), objectClass, getOrgInfo(), null);
    }

    @Override
    public String checkAccessToken() {
        return null;
    }

    @Override
    public String create(String s, AbstractConfigTO abstractConfigTO) {

        adCreate.create();
        return null;
    }

    @Override
    public String update(String s, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    @Override
    public String delete(DeleteInfo deleteInfo, AbstractConfigTO abstractConfigTO) {
        return null;
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
    public String search(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    @Override
    public String listChild(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    public void test() {
        adConfig.getAdConnection().test();
    }

    public Set<Attribute> getOrgInfo() {
        Set<Attribute> attrs = new HashSet<Attribute>();


        // V 2.0 其他数据准备
        String nameString = ADSyncUtils.dataCreateUtil("name", "Sync");
        attrs.add(AttributeBuilder.build("__NAME__", "上海派拉技术有限公司3333"));
        attrs.add(AttributeBuilder.build("__ORG__", ""));
        return attrs;
    }
}
