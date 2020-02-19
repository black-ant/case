package com.gang.study.adtest.demo.utils;

import com.gang.study.adtest.demo.to.AbstractBaseBean;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @Classname ADSyncUtils
 * @Description TODO
 * @Date 2019/12/5 17:02
 * @Created by zengzg
 */
public final class ADSyncUtils {

    private static Logger LOG = LoggerFactory.getLogger(ADSyncUtils.class);

    private ADSyncUtils() {

    }

    public static String dataCreateUtil(String type, String baseData) {
        String cdata = "111111";
        switch (type) {
            case "name":
                cdata = baseData + new Random().nextInt(666);
                break;
            default:
                break;
        }
        return cdata;
    }

    public static Set<Attribute> getAttributeInfo(AbstractBaseBean baseBean) {

        List<Field> fields = FieldUtils.getAllFieldsList(baseBean.getClass());
        Set<Attribute> attributes = new HashSet<Attribute>();
        fields.stream().forEach(field -> {
            try {
                if (field.getAnnotation(ADAnnotation.class) != null && null != field.get(baseBean)) {
                    if ("memberOf".equals(field.getAnnotation(ADAnnotation.class).alias()) || "ldapGroups".equals(field.getAnnotation(ADAnnotation.class).alias())) {
                        String[] memberOf = (String[]) field.get(baseBean);
                        attributes.add(AttributeBuilder.build(field.getAnnotation(ADAnnotation.class).alias(),
                                memberOf));
                    } else {
                        attributes.add(AttributeBuilder.build(field.getAnnotation(ADAnnotation.class).alias(),
                                field.get(baseBean)));
                    }

                } else {
                    LOG.info("------> this field :{} is not need edit <-------", field.getName());
                }
            } catch (IllegalAccessException e) {
                LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
            }
        });
        //        String[] serverArrays = {"71072553-9F6F-44B7-9C75-316958F7DA3D",};
        //        attributes.add(AttributeBuilder.build("memberOf", serverArrays));
        return attributes;

    }


}
