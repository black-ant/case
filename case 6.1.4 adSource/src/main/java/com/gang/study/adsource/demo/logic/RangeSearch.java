package com.gang.study.adsource.demo.logic;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname RangeSearch
 * @Description TODO
 * @Date 2020/3/1 20:26
 * @Created by zengzg
 */
@Component
public class RangeSearch {


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

    // http://www.wendangku.net/doc/026f93c7c8d376eeaeaa3191-10.html
    public void search(String ouName, Integer page, Integer size) throws NamingException {

        if (null == ctx) {
            init();
        }

        logger.info("------> this is in rangeSearch <-------");
        try {

            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String searchFilter = "(&(objectClass=user))";
            String searchBase = "OU=" + ouName + ",DC=wdhacpoc,DC=com,DC=cn";


            int totalResults = 0;

            String sortkey = "uid";
            int start = page * size;
            int end = start + size;

            // targetOffset ：  起始显示条数。
            // afterCount： 从起始开始计算，显示多少条。
            // Control.CRITICAL : 开启 control
            VirtualListViewControl vctl = new VirtualListViewControl(0, 0, 0, 3, Control.CRITICAL);
            ctx.setRequestControls(new Control[]{new SortControl(sortkey, Control.CRITICAL), vctl});

            String retAtt[] = {"uid", "name", "cn"};
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constraints.setReturningAttributes(retAtt);

            logger.info("------> this is filter :{} <-------", searchFilter);
            NamingEnumeration results = ctx.search(searchBase, searchFilter, constraints);

            while (results.hasMoreElements()) {
                SearchResult sr = (SearchResult) results.next();
                logger.info("------> this search item :{}  <-------", sr.getName());
            }

            System.out.println("Total members: " + totalResults);
            ctx.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
