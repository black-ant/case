package com.gang.study.adtest.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.adtest.demo.to.ADBaseTO;
import com.gang.study.adtest.demo.to.ADGroupTO;
import com.gang.study.adtest.demo.to.ADOrgTO;
import com.gang.study.adtest.demo.to.ADSearchType;
import com.gang.study.adtest.demo.to.ADUserTO;
import net.tirasa.adsddl.ntsd.utils.GUID;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Classname ADSearchUtils
 * @Description TODO
 * @Date 2019/12/6 14:43
 * @Created by zengzg
 */
public final class ADSearchUtils {

    private static Logger LOG = LoggerFactory.getLogger(ADSearchUtils.class);

    private ADSearchUtils() {

    }

    public static List<ADBaseTO> searchInvoke(ObjectClass objectClass, ADUtilities utilities, String baseContext,
                                              LdapConnection connction) {
        Set<Attributes> backSet = utilities.searchAttrByType(objectClass, getNativeFilter(ADSearchType.ALL, null,
                null, objectClass), baseContext, connction);
        List<ADBaseTO> list = getBaseTOList(objectClass, backSet);
        LOG.info("------> backset :{} <-------", JSONObject.toJSONString(list));
        return list;
    }


    public static List<ADBaseTO> getTO(ObjectClass objectClass, ADUtilities utilities, String key, String value,
                                       String baseContext, LdapConnection connction) {

        Set<Attributes> backSet = utilities.searchAttrByType(objectClass, getNativeFilter(ADSearchType.EQUALS, key,
                value, objectClass), baseContext, connction);
        List<ADBaseTO> list = getBaseTOList(objectClass, backSet);
        LOG.info("------> backset :{} <-------", JSONObject.toJSONString(list));
        return list;
    }

    public static List<ADBaseTO> getBaseTOList(ObjectClass objectClass, Set<Attributes> backSet) {
        List<ADBaseTO> adBaseTOS = new LinkedList<>();
        backSet.forEach(set -> {
            adBaseTOS.add(searchTO(objectClass, set));
        });
        return adBaseTOS;
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


    public static ADBaseTO searchTO(ObjectClass objectClass, Attributes attributes) {
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


    public static ADBaseTO exchangeBaseTO(ADBaseTO baseTO, Attributes attributes) {
        List<Field> fields = FieldUtils.getAllFieldsList(baseTO.getClass());
        LOG.info("---> {}", attributes);
        fields.stream().forEach(field -> {
            if (null != field.getAnnotation(ADAnnotation.class)) {
                String adAlias = field.getAnnotation(ADAnnotation.class).alias();
                field.setAccessible(true);
                try {
                    FieldUtils.writeField(field, baseTO,
                            ADUtilities.getAttributorValue(attributes.get(adAlias.toLowerCase())));
                } catch (IllegalAccessException e) {
                    LOG.info("------> this Param Write failure  <-------");
                }
            }

        });

        baseTO.setName(ADUtilities.getAttributorValue(attributes.get("name")));
        try {
            baseTO.setKey(GUID.getGuidAsString((byte[]) attributes.get("objectguid").get()));
        } catch (NamingException e) {
            LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
        return baseTO;
    }

    public static String getEqualsFilter(String key, String value) {
        if ("objectGUID".equals(key)) {
            return "(" + key + "=" + ADUtilities.getGUID(value) + ")";
        }
        return "(" + key + "=" + value + ")";
    }

    public static String getSearchClassFilter(ObjectClass objectClass) {
        return "(objectClass=" + ADUtilities.editOclass(objectClass) + ")";
    }

}
