package com.gang.study.adsource.demo.logic;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname UserLofic
 * @Description TODO
 * @Date 2020/2/19 16:14
 * @Created by zengzg
 */
@Component
public class UserLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Integer REMOVE_ATTRIBUTE = 3;
    private final static Integer REPLACE_ATTRIBUTE = 2;
    private final static Integer ADD_ATTRIBUTE = 1;

    private Set<String> orgClass;

    @Autowired
    private BaseLogic baseLogic;

    private static LdapContext ctx;


    public void init() {
        // 必要数据初始化
        orgClass = new TreeSet<>();
        orgClass.add("organizationalPerson");
        orgClass.add("top");
        orgClass.add("person");
        orgClass.add("user");

        // 初始化信息
        baseLogic.init();

        // step 1 : 构建容器
        ctx = baseLogic.createLdapContext();

        if (baseLogic.checkAlive(ctx)) {
            logger.info("------> this context is connect <-------");
        }

    }


    /**
     * 创建组织
     *
     * @param ouName
     * @throws NamingException
     */
    public void create(String ouName) throws NamingException {

        if (null == ctx) {
            init();
        }

        // step2 : 准备属性
        final BasicAttributes adAttrs = getAttriutes();

        // step 3 : 创建对象
        Context context = ctx.createSubcontext("CN=" + ouName + ",OU=武汉研发194,DC=wdhacpoc,DC=com,DC=cn",
                adAttrs);

        logger.info("------> this in ok :{} <-------", JSONObject.toJSONString(context));
    }

    public void update(String ouName) throws NamingException {
        if (null == ctx) {
            init();
        }

        // step2 : 准备属性
        final BasicAttributes updateAttrs = getUpdateAttriutes();

        //        editAttribute("CN=" + ouName + ",OU=武汉研发194,DC=wdhacpoc,DC=com,DC=cn", updateAttrs);
        addGroup(ouName);
        //        ctx.modifyAttributes("CN=" + ouName + ",OU=武汉研发194,DC=wdhacpoc,DC=com,DC=cn", 3, getUpdateAttriutes
        //        ());
    }

    public void addGroup(String ouName) {
        logger.info("------> this is add Group <-------");
        String memberAttr = "member";
        String memberValue = "CN=" + ouName + ",OU=武汉研发194,DC=wdhacpoc,DC=com,DC=cn";
        String groupDN = "CN=Sync126,DC=wdhacpoc,DC=com,DC=cn";
        //        addMemberToGroup(memberAttr, memberValue, groupDN);
        removeMemberToGroup(memberAttr, memberValue, groupDN);
    }

    private void addMemberToGroup(String memberAttr, String memberValue, String groupDN) {
        BasicAttribute attr = new BasicAttribute(memberAttr, memberValue);
        ModificationItem item = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                attr);

        try {
            ctx.modifyAttributes(groupDN, new ModificationItem[]{item});
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    private void removeMemberToGroup(String memberAttr, String memberValue, String groupDN) {
        BasicAttribute attr = new BasicAttribute(memberAttr, memberValue);
        ModificationItem item = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                attr);

        try {
            ctx.modifyAttributes(groupDN, new ModificationItem[]{item});
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    public void editAttribute(String dn, Attributes attributes) {

        final List<ModificationItem> modItems = new ArrayList<ModificationItem>(attributes.size());

        NamingEnumeration<? extends Attribute> attrEnum = attributes.getAll();

        while (attrEnum.hasMoreElements()) {
            final javax.naming.directory.Attribute attr = attrEnum.nextElement();
            modItems.add(new ModificationItem(REPLACE_ATTRIBUTE, attr));
        }
        logger.info("------> DN IS :{} <-------", dn);
        try {
            ctx.modifyAttributes(dn, modItems.toArray(new ModificationItem[modItems.size()]));
        } catch (NamingException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }


    public BasicAttributes getAttriutes() {
        BasicAttributes adAttrs = new BasicAttributes(true);
        adAttrs.put(getAttribute("description", "test"));

        BasicAttribute objectClass = new BasicAttribute("objectClass");
        for (String ldapClass : orgClass) {
            objectClass.add(ldapClass);
        }
        adAttrs.put(objectClass);

        return adAttrs;
    }

    public BasicAttributes getUpdateAttriutes() {
        BasicAttributes adAttrs = new BasicAttributes(true);

        //        adAttrs.put(getAttribute("description", "test11"));
        adAttrs.put(getAttribute("info", "test"));

        return adAttrs;
    }

    public BasicAttribute getAttribute(String key, String value) {
        return new BasicAttribute(key, value);
    }

}
