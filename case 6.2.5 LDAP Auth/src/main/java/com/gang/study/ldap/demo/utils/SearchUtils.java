//CHECKSTYLE:OFF
package com.gang.study.ldap.demo.utils;

import org.ldaptive.schema.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.List;

public final class SearchUtils {

    private static Logger LOG = LoggerFactory.getLogger(SearchUtils.class);

    public static final String COOKIE = "cookie";


    /**
     * 通过主键(ObjectGUID , distinguishedName ,Sa)
     *
     * @param connect
     * @param key
     * @return
     */
    public static Attributes doSarchByObjectKey(LdapContext connect, String key, String baseContext) {
        List<Attributes> list = new ArrayList<>();

        try {

            String searchFilter = "(cn=" + key + ")";
            list = SearchUtils.doSearchByFilter(connect, baseContext,
                    SearchControls.SUBTREE_SCOPE, searchFilter);
        } catch (NamingException e) {
            LOG.error("E---->searchByObjectKey  error :{} -- content :{}", e.getClass(), e.getMessage());
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public static List<Attributes> doSearchByFilter(LdapContext ctx, String BaseContext, Integer scope,
                                                    String searchFilter) throws NamingException {

        LOG.info("------> Search Filter :{} <-------", searchFilter);

        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(scope);

        // 额外操作 : 定义查询属性 , 此处可以一定程度优化查询效率,  可以定制
        //        String[] attrPersonArray = {"uid", "userPassword", "displayName", "cn", "sn", "mail", "description"};
        //        searchCtls.setReturningAttributes(attrPersonArray);

        List<Attributes> attrList = new ArrayList<>();
        final NamingEnumeration<SearchResult> ne = ctx.search(BaseContext, searchFilter, searchCtls);
        while (ne.hasMoreElements()) {
            SearchResult sr = ne.next();
        }
        return attrList;
    }


    /**
     * Search : 获取 DN 对应的属性
     *
     * @param ctx
     * @param entryDN
     * @param attributes
     * @return
     */
    public static Attributes getAttributesByEntryDN(final LdapContext ctx, final String entryDN,
                                                    final String... attributes) {
        try {
            return ctx.getAttributes(entryDN, attributes);
        } catch (NamingException e) {
            LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过 Guid 获取所有属性
     *
     * @param ctx
     * @param guid
     * @return
     * @throws NamingException
     */
    public static Attributes searchByGUID(LdapContext ctx, String guid) throws NamingException {
        return ctx.getAttributes("<GUID=" + guid + ">");
    }


    public static Attribute getAttributedByGUID(LdapContext ctx, String guid, String fieldKey) {
        try {
            Attributes attributes = searchByGUID(ctx, guid);
            return attributes.get(fieldKey);
        } catch (NamingException e) {
            LOG.error("E----> getAttributedByGUID Error :{} -- content :{}", e.getClass(), e.getMessage());
        }
        return null;
    }


}
