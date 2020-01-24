package com.gang.ext.sdk.workwechat.logic;

import com.alibaba.fastjson.JSONObject;
import com.gang.ext.sdk.workwechat.to.OrgTO;
import com.gang.ext.sdk.workwechat.to.WorkWechatConfig;
import com.gang.ext.sdk.workwechat.type.WorkWechatAPI;
import com.gang.ext.sdk.workwechat.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname OrgLogic
 * @Description TODO
 * @Date 2019/12/25 22:43
 * @Created by zengzg
 */
@Component
public class OrgLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseLogic baseLogic;


    public String init(WorkWechatConfig config) {
        return baseLogic.getToken(JSONObject.toJSONString(config));
    }

    public void create() {

    }

    public String create(OrgTO createTO, WorkWechatConfig config) {
        logger.info("------> this is in create <-------");
        String url = WorkWechatAPI.API_ORG_CREATE.getRestAPI().replace(WorkWechatAPI.TOKEN_CODE.getRestAPI(),
                init(config));
        logger.info("------> get url :{} <-------", url);
        String jsonObject = HttpClientUtils.sendHttpPost(url, JSONObject.toJSONString(createTO));

        return jsonObject;

    }

    public String update(OrgTO updateTO, WorkWechatConfig config) {
        String url = WorkWechatAPI.API_ORG_UPDATE.getRestAPI().replace(WorkWechatAPI.TOKEN_CODE.getRestAPI(),
                init(config));
        String jsonObject = HttpClientUtils.sendHttpPost(url, JSONObject.toJSONString(updateTO));
        return jsonObject;
    }

    public String delete(String key, WorkWechatConfig config) {
        String url = WorkWechatAPI.API_ORG_DELETE.getRestAPI()
                .replace(WorkWechatAPI.TOKEN_CODE.getRestAPI(), init(config))
                .replace("ID", key);
        String jsonObject = HttpClientUtils.doGet(url);

        // TODO : DO AFTER

        return jsonObject;
    }

    public WorkWechatConfig checkConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public String get(String key, WorkWechatConfig config) {
        /* String token = new WorkWechatTokenUtil(config).getToken(); */
        String url = WorkWechatAPI.API_ORG_SEARCH.getRestAPI()
                .replace(WorkWechatAPI.TOKEN_CODE.getRestAPI(), init(config))
                .replace("ID", key);
        String jsonObject = HttpClientUtils.doGet(url);
        return jsonObject;
    }

    public String list(String searchKey, WorkWechatConfig config) {
        // TODO Auto-generated method stub
        return null;
    }

    public String listChild(String searchKey, WorkWechatConfig config) {
        String url = WorkWechatAPI.API_ORG_SEARCH.getRestAPI()
                .replace(WorkWechatAPI.TOKEN_CODE.getRestAPI(), init(config))
                .replace("ID", searchKey);
        String jsonObject = HttpClientUtils.doGet(url);

        /*
         * Gson gson = new Gson(); WorkWechatDept dept = gson.fromJson(jsonObject,
         * WorkWechatDept.class);
         */

        /*
         * WorkWechatDept dept = JSONObject.toJavaObject(jsonObject2,
         * WorkWechatDept.class);
         */
        return jsonObject;
    }

}
