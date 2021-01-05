package com.gang.study.dingtalk.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentDeleteRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.gang.study.dingtalk.demo.to.OrgTO;
import com.gang.study.dingtalk.demo.type.DingTalkAPIEnum;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname OrgService
 * @Description TODO
 * @Date 2020/1/19 14:26
 * @Created by zengzg
 */
@Component
public class OrgService extends AbstractDingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createOrg(String parentId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_CREATE_API.getUrl());
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        request.setParentid("1");
        request.setCreateDeptGroup(true);
        request.setOrder("100");
        request.setName("部门");
        OapiDepartmentCreateResponse response = client.execute(request, dingTalkToken.getToken());
        return "";
    }

    public OapiDepartmentGetResponse getOrg(String orgId) throws ApiException {

        logger.info("------> 查询单个部分详细信息 :{} --- url :{} <-------", orgId, DingTalkAPIEnum.ORG_GET_API.getUrl());

        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_GET_API.getUrl());
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(orgId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, dingTalkToken.getToken());
        logger.info("------> response is :{} <-------", response);
        return response;
    }

    public List<OapiDepartmentListResponse.Department> getOrgList(String parentId) throws ApiException {

        logger.info("------> 获取部门列表 :{} --- url :{} <-------", parentId, DingTalkAPIEnum.ORG_SEARCH.getUrl());

        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_SEARCH.getUrl());
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(parentId);
        request.setFetchChild(Boolean.TRUE);
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dingTalkToken.getToken());
        logger.info("------> response is :{} <-------", response.getDepartment());
        return response.getDepartment();
    }


    public String create(OrgTO orgTO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_CREATE_API.getUrl());
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();

        BeanUtils.copyProperties(orgTO, request);
        OapiDepartmentCreateResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", JSONObject.toJSONString(response == null ? "no response" :
                response));
        return JSONObject.toJSONString(response == null ? "" : response);
    }

    public String update(OrgTO orgTO) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_UPDATE_API.getUrl());
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();

        BeanUtils.copyProperties(orgTO, request);
        request.setId(Long.valueOf(orgTO.getId()));

        OapiDepartmentUpdateResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", response.getErrmsg());
        return JSONObject.toJSONString(response == null ? "" : response);
    }

    public String delete(String orgId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.ORG_DELETE_API.getUrl());
        OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
        request.setId(orgId);
        request.setHttpMethod("GET");
        OapiDepartmentDeleteResponse response = client.execute(request, dingTalkToken.getToken());

        logger.info("------> response is :{} <-------", response.getErrmsg());
        return JSONObject.toJSONString(response == null ? "" : response);
    }

}
