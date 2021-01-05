package com.gang.study.dingtalk.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserCreateRequest;
import com.dingtalk.api.request.OapiUserDeleteRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserDeleteResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.gang.study.dingtalk.demo.to.UserTO;
import com.gang.study.dingtalk.demo.type.DingTalkAPIEnum;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/1/19 14:26
 * @Created by zengzg
 */
@Component
public class UserService extends AbstractDingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public OapiUserGetResponse getUser(String userId) throws ApiException {

        logger.info("------> 查询单个部分详细信息 :{} --- url :{} <-------", userId, DingTalkAPIEnum.USER_GET_API.getUrl());

        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.USER_GET_API.getUrl());
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, dingTalkToken.getToken());
        logger.info("------> response is :{} <-------", response);
        return response;
    }

    public List<OapiUserSimplelistResponse.Userlist> getUserList(String parentId) throws ApiException {

        logger.info("------> 获取部门列表 :{} --- url :{} <-------", parentId, DingTalkAPIEnum.USER_SEARCH.getUrl());

        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.USER_SEARCH.getUrl());
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(Long.valueOf(parentId));
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, dingTalkToken.getToken());
        logger.info("------> response is :{} <-------", response.getUserlist());
        return response.getUserlist();
    }


    public String create(UserTO userTO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.USER_CREATE_API.getUrl());
        OapiUserCreateRequest request = new OapiUserCreateRequest();

        BeanUtils.copyProperties(userTO, request);
        OapiUserCreateResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", JSONObject.toJSONString(response == null ? "no response" :
                response));
        return JSONObject.toJSONString(response == null ? "" : response);
    }

    public String update(UserTO userTO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.USER_UPDATE_API.getUrl());
        OapiUserUpdateRequest request = new OapiUserUpdateRequest();

        BeanUtils.copyProperties(userTO, request);
        OapiUserUpdateResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", response.getErrmsg());
        return JSONObject.toJSONString(response == null ? "" : response);
    }

    public String delete(String userId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.USER_DELETE_API.getUrl());
        OapiUserDeleteRequest request = new OapiUserDeleteRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserDeleteResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", response.getErrmsg());
        return JSONObject.toJSONString(response == null ? "" : response);
    }
}
