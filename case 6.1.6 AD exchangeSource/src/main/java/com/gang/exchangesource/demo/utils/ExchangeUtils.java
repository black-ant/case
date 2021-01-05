package com.gang.exchangesource.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gang.exchangesource.demo.to.ExchangeTO;
import com.gang.exchangesource.demo.to.MailSystemConfig;
import com.gang.exchangesource.demo.to.SyncBaseBean;
import com.gang.exchangesource.type.ADAnnotation;
import com.gang.exchangesource.type.ExchangeType;
import com.gang.exchangesource.type.OperationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ExchangeUtils
 * @Description TODO
 * @Date 2020/3/3 18:21
 * @Created by zengzg
 */
public class ExchangeUtils {

    private static Logger LOG = LoggerFactory.getLogger(ExchangeUtils.class);

    /**
     * 获取命令集
     *
     * @param config
     * @param exchangeTO
     * @param exchangeType
     * @return
     */
    public static String getCommonStr(MailSystemConfig config, ExchangeTO exchangeTO, ExchangeType exchangeType) {
        JSONObject commond = getAttrsInfoByBean(config);
        JSONArray attrArray = getAttrsArrayByBean(exchangeTO);
        commond.put("param", attrArray);
        commond.put("cmdlet", exchangeType.getCode());
        //        commond.put("cmdlet", ExchangeType.OP_NEW_GROUP.getCode());
        return commond.toJSONString();
    }

    /**
     * Get Map By Bean
     *
     * @param baseBean
     * @return
     */
    public static JSONArray getAttrsArrayByBean(SyncBaseBean baseBean) {

        List<Field> fields = FieldUtils.getAllFieldsList(baseBean.getClass());
        JSONArray array = new JSONArray();
        fields.stream().forEach(field -> {
            field.setAccessible(Boolean.TRUE);
            try {
                if (field.getAnnotation(ADAnnotation.class) != null && null != field.get(baseBean)) {
                    array.add(getTOItem(field.getAnnotation(ADAnnotation.class).alias(), (String) field.get(baseBean)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
        return array;

    }

    public static JSONObject getAttrsInfoByBean(SyncBaseBean baseBean) {

        List<Field> fields = FieldUtils.getAllFieldsList(baseBean.getClass());
        JSONObject object = new JSONObject();
        fields.stream().forEach(field -> {
            field.setAccessible(Boolean.TRUE);
            try {
                if (field.getAnnotation(ADAnnotation.class) != null && null != field.get(baseBean)) {
                    object.put(field.getAnnotation(ADAnnotation.class).alias(), (String) field.get(baseBean));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
        return object;

    }

    public static JSONObject getTOItem(String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", key);
        jsonObject.put("value", value);
        return jsonObject;

    }
}
