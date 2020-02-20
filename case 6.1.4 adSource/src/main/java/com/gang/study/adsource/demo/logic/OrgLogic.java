package com.gang.study.adsource.demo.logic;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.adsource.demo.to.ObjectClass;
import com.gang.study.adsource.demo.type.ADSearchType;
import com.gang.study.adsource.demo.utils.ADSearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname OrgLogic
 * @Description TODO
 * @Date 2020/2/19 10:16
 * @Created by zengzg
 */
@Component
public class OrgLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Set<String> orgClass;

    @Autowired
    private BaseLogic baseLogic;

    private static LdapContext ctx;


    public void init() {
        // 必要数据初始化
        orgClass = new TreeSet<>();
        orgClass.add("organizationalUnit");
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
    public void createOrg(String ouName) throws NamingException {

        // step2 : 准备属性
        final BasicAttributes adAttrs = getAttriutes();

        // step 3 : 创建对象
        Context context = ctx.createSubcontext("OU=" + ouName + ",DC=wdhacpoc,DC=com,DC=cn", adAttrs);

        logger.info("------> this in ok :{} <-------", JSONObject.toJSONString(context));
    }

    /**
     * 更新组织
     *
     * @param oldName
     * @param newName
     * @throws NamingException
     */
    public void update(String oldName, String newName) throws NamingException {
        ctx.rename("OU=" + oldName + ",DC=wdhacpoc,DC=com,DC=cn", "OU=" + newName + ",DC=wdhacpoc,DC=com,DC=cn");
    }

    public void search(String info, String baseOUName) throws NamingException {

        // step 1 : 搜索根路径 --> baseDN
        baseOUName = "OU=" + baseOUName + ",DC=wdhacpoc,DC=com,DC=cn";

        // step 2 : 定义搜索范围
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // step 3 : 准备搜索语句
        String searchFilter = ADSearchUtils.getNativeFilter(ADSearchType.EQUALS, "name",
                info, ObjectClass.ORGANIZATION);

        // step 4 : 搜索
        try {
            final NamingEnumeration<SearchResult> results = ctx.search(baseOUName, searchFilter, searchCtls);

            // step 5 : 处理结果
            Set backMap = new HashSet();
            while (results.hasMoreElements()) {
                SearchResult sr = (SearchResult) results.next();
                logger.info("------> this is result :{} <-------", sr.getAttributes().get("name"));
                backMap.add(sr.getAttributes());
            }

            logger.info("------> this back is :{} <-------", backMap.size());
        } catch (NamingException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 删除组织
     *
     * @param ouName
     * @throws NamingException
     */
    public void delete(String ouName) throws NamingException {
        ctx.destroySubcontext("OU=" + ouName + ",DC=wdhacpoc,DC=com,DC=cn");
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
