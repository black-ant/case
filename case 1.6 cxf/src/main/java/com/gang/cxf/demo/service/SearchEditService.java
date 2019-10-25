package com.gang.cxf.demo.service;

import org.apache.cxf.jaxrs.ext.search.SearchBean;
import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.apache.cxf.jaxrs.ext.search.SearchContextProvider;
import org.apache.cxf.message.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SearchEditService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void exchange() {
//        getSearchCond("(username==gang)");

        getSearchCond("username==gang,age==777");
    }


    protected void getSearchCond(final String fiql) {
        try {
            SearchContext searchContext = new SearchContextProvider().createContext(new MessageImpl());
            SearchCondition<SearchBean> sc = searchContext.getCondition(fiql, SearchBean.class);
            logger.info("------> this is :{} <-------", sc);

        } catch (Exception e) {
            throw e;
        }
    }
}
