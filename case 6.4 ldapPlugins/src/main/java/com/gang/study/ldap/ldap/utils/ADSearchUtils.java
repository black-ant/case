package com.gang.study.ldap.ldap.utils;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.ldap.ldap.to.ADBaseTO;
import com.gang.study.ldap.ldap.to.ADGroupTO;
import com.gang.study.ldap.ldap.to.ADOrgTO;
import com.gang.study.ldap.ldap.to.ADSearchType;
import com.gang.study.ldap.ldap.to.ADUserTO;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.LdapType;
import net.tirasa.connid.bundles.ldap.commons.LdapUtil;
import net.tirasa.connid.bundles.ldap.search.LdapSearches;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname ADSearchUtils
 * @Description TODO
 * @Date 2019/12/11 17:53
 * @Created by zengzg
 */
public final class ADSearchUtils {

    private static Logger LOG = LoggerFactory.getLogger(ADSearchUtils.class);

    private ADSearchUtils() {

    }

    /**
     * invvoke Set<Attributes>
     *
     * @param objectClass
     * @param baseContext
     * @param connction
     * @return
     */
    public static List<ADBaseTO> searchInvoke(ObjectClass objectClass, String baseContext, LdapConnection connction) {
        Set<Attributes> backSet = LdapUtil.searchAttrByType(objectClass, getNativeFilter(ADSearchType.ALL, null, null, objectClass), baseContext, connction);
        List<ADBaseTO> list = getBaseTOList(objectClass, backSet);
        LOG.info("------> backset :{} <-------", JSONObject.toJSONString(list));
        return list;
    }

    /**
     * invoke ConnectorObject
     *
     * @param objectClass
     * @param baseContext
     * @param connction
     * @return
     */
    public static List<ADBaseTO> searchInvokeType(ObjectClass objectClass, String baseContext, LdapConnection connction) {

        String[] array = getSearchFiled(objectClass);
        List<ConnectorObject> back = LdapSearches.findObjects(connction, objectClass, baseContext, AttributeBuilder.build("objectClass", LdapUtil.editOclass(objectClass)), array);

        List<ADBaseTO> list = getObjectTOList(objectClass, back);
        LOG.info("------> backset :{} <-------", JSONObject.toJSONString(list));
        return null;
    }


    public static List<ADBaseTO> getTO(ObjectClass objectClass, String key, String value, String baseContext, LdapConnection connction) {

        Set<Attributes> backSet = LdapUtil.searchAttrByType(objectClass, getNativeFilter(ADSearchType.EQUALS, key, value, objectClass), baseContext, connction);
        List<ADBaseTO> list = getBaseTOList(objectClass, backSet);
        LOG.info("------> backset :{} <-------", JSONObject.toJSONString(list));
        return list;
    }

    public static List<ADBaseTO> getBaseTOList(ObjectClass objectClass, Set<Attributes> backSet) {
        List<ADBaseTO> adBaseTOS = new LinkedList<>();
        backSet.forEach(item -> {
            adBaseTOS.add(searchTO(objectClass, exchangeMap(item)));
        });
        return adBaseTOS;
    }

    /**
     * exchange
     *
     * @param attributes
     * @return
     */
    public static Map<String, Attribute> exchangeMap(Attributes attributes) {
        HashMap<String, Attribute> back = new HashMap<>();
        NamingEnumeration<?> attrs = attributes.getAll();
        while (attrs.hasMoreElements()) {
            Attribute sr = null;
            try {
                sr = (Attribute) attrs.next();
            } catch (NamingException e) {
                LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
            }
            back.put(sr.getID().toLowerCase(), sr);
        }
        return back;

    }


    /**
     * ConnectorObject to ADBaseTO
     *
     * @param objectClass
     * @param objects
     * @return
     */
    public static List<ADBaseTO> getObjectTOList(ObjectClass objectClass, List<ConnectorObject> objects) {
        List<ADBaseTO> adBaseTOS = new LinkedList<>();
        objects.forEach(item -> {
            LOG.info("------> item :{} <-------", item.getAttributes());
            adBaseTOS.add(searchTO(objectClass, exchangeMap(item.getAttributes())));
        });
        return adBaseTOS;
    }

    /**
     * @param attributes
     * @return
     */
    public static HashMap<String, org.identityconnectors.framework.common.objects.Attribute> exchangeMap(Set<org.identityconnectors.framework.common.objects.Attribute> attributes) {
        HashMap<String, org.identityconnectors.framework.common.objects.Attribute> back = new HashMap<>();

        attributes.stream().forEach(item -> {
            back.put(item.getName().toLowerCase(), item);
        });

        return back;
    }

    public static String getNativeFilter(ADSearchType adSearchType, String key, String value, ObjectClass objectClass) {
        StringBuilder backFilter = new StringBuilder();
        backFilter.append("(");
        switch (adSearchType) {
            case EQUALS:
                backFilter.append("&");
                backFilter.append(getSearchClassFilter(objectClass)).append(getEqualsFilter(key, value));
                break;
            case ALL:
                backFilter.append("&");
                backFilter.append(getSearchClassFilter(objectClass));
                break;
            case OR:
                backFilter.append("|");
                backFilter.append(getSearchClassFilter(objectClass)).append(getEqualsFilter(key, value));
                break;
            default:
                ;

        }
        backFilter.append(")");
        return backFilter.toString();
    }

    /**
     * Map 转 ADBaseTO
     *
     * @param objectClass
     * @param attributes
     * @param <T>
     * @return
     */
    public static <T> ADBaseTO searchTO(ObjectClass objectClass, Map<String, T> attributes) {
        switch (objectClass.getObjectClassValue()) {
            case "__ORGANIZATION__":
                return exchangeBaseTO(new ADOrgTO(), attributes);
            case "__ACCOUNT__":
                return exchangeBaseTO(new ADUserTO(), attributes);
            case "__GROUP__":
                return exchangeBaseTO(new ADGroupTO(), attributes);
            default:
                return null;
        }
    }

    /**
     * 编辑查询列表
     *
     * @return
     * @Param ObjectClass objectClass
     */
    public static String[] getSearchFiled(ObjectClass objectClass) {
        switch (objectClass.getObjectClassValue()) {
            case "__ORGANIZATION__":
                return getSearchFiled(ADOrgTO.class);
            case "__ACCOUNT__":
                return getSearchFiled(ADUserTO.class);
            case "__GROUP__":
                return getSearchFiled(ADGroupTO.class);
            default:
                return null;
        }
    }

    public static String[] getSearchFiled(Class clazz) {
        LinkedList<String> backField = new LinkedList<String>();
        for (Field field : clazz.getFields()) {
            if (null != field.getAnnotation(ADAnnotation.class)) {
                backField.add(field.getAnnotation(ADAnnotation.class).alias());
            }
        }
        backField.add("entryUUID");
        backField.add("__NAME__");
        return backField.toArray(new String[]{});
    }


    public static <T> ADBaseTO exchangeBaseTO(ADBaseTO baseTO, Map<String, T> attributes) {
        List<Field> fields = FieldUtils.getAllFieldsList(baseTO.getClass());
        LOG.info("---> {}", attributes);
        fields.stream().forEach(field -> {
            if (null != field.getAnnotation(ADAnnotation.class)) {
                String adAlias = field.getAnnotation(ADAnnotation.class).alias();
                field.setAccessible(true);
                try {
                    if (null != attributes.get(adAlias.toLowerCase())) {
                        String value = LdapUtil.getAttributorValue(attributes.get(adAlias.toLowerCase()));
                        if (!StringUtils.isEmpty(value)) {
                            FieldUtils.writeField(field, baseTO, value);
                        }
                    } else {
                        LOG.info("------> param {} is null <-------", adAlias.toLowerCase());
                    }

                } catch (IllegalAccessException e) {
                    LOG.info("------> this Param Write failure  <-------");
                }
            }

        });

        baseTO.setName(LdapUtil.getAttributorValue(attributes.get("name")));
        baseTO.setKey(LdapUtil.getGuidAsString(LdapUtil.getAttributorValue(attributes.get(LdapType.OBJECTGUID.toLowerCase())).getBytes()));
        return baseTO;
    }

    public static String getEqualsFilter(String key, String value) {
        if ("objectGUID".equals(key)) {
            return "(" + key + "=" + LdapUtil.getGUID(value) + ")";
        }
        return "(" + key + "=" + value + ")";
    }

    public static String getSearchClassFilter(ObjectClass objectClass) {
        return "(objectClass=" + LdapUtil.editOclass(objectClass) + ")";
    }

}

