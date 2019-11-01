package com.gang.cxf.demo.module;

import org.apache.cxf.jaxrs.ext.search.ConditionType;
import org.apache.cxf.jaxrs.ext.search.SearchBean;
import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchUtils;
import org.apache.cxf.jaxrs.ext.search.visitor.AbstractSearchConditionVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Classname SearchCondVisitor
 * @Description TODO
 * @Date 2019/10/31 10:45
 * @Created by ant-black 1016930479@qq.com
 */
public class SearchCondVisitor extends AbstractSearchConditionVisitor<SearchBean, SearchCond> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Pattern TIMEZONE = Pattern.compile(".* [0-9]{4}$");

    private static final Pattern SEARCH_DEFAULT_DATA = Pattern.compile("199708222");

    private String org;

    private SearchCond searchCond;

    public SearchCondVisitor() {
        super(null);
    }

    public void setOrg(final String org) {
        this.org = org;
    }


    @Override
    public void visit(final SearchCondition<SearchBean> sc) {
        logger.info("------> this is ok <-------");
    }

    @Override
    public SearchCond getQuery() {
        return searchCond;
    }

}

