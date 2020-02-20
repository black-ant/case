package com.gang.study.adsource.demo.logic;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapContext;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname GroupLogic
 * @Description TODO
 * @Date 2020/2/19 16:18
 * @Created by zengzg
 */
@Component
public class GroupLogic {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Set<String> orgClass;

    @Autowired
    private BaseLogic baseLogic;

    private static LdapContext ctx;


    public void init() {
        // 必要数据初始化
        orgClass = new TreeSet<>();
        orgClass.add("group");
        orgClass.add("top");

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
        Context context = ctx.createSubcontext("DC=" + ouName + ",OU=武汉研发194,DC=wdhacpoc,DC=com,DC=cn",
                adAttrs);

        logger.info("------> this in ok :{} <-------", JSONObject.toJSONString(context));
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

    public BasicAttribute getAttribute(String key, String value) {
        return new BasicAttribute(key, value);
    }


}
