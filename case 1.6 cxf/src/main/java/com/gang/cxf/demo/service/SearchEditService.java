package com.gang.cxf.demo.service;

import com.gang.cxf.demo.module.SearchCond;
import com.gang.cxf.demo.module.SearchCondVisitor;
import org.apache.cxf.jaxrs.ext.search.*;
import org.apache.cxf.message.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchEditService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void exchange() {
//        getSearchCond("(username==gang)");

//        getSearchCond("username==gang,age==777");
        getSearchCond("(fullName==*tgt*;fullName==*oidc*),(fullName==*oidc*)");
    }


    protected void getSearchCond(final String fiql) {
        try {
            SearchContext searchContext = new SearchContextProvider().createContext(new MessageImpl());
            SearchCondition<SearchBean> sc = searchContext.getCondition(fiql, SearchBean.class);
            logger.info("------> this is :{} <-------", sc);
            StringBuilder fiqlSQL = getQuery(sc.getSearchConditions());
        } catch (Exception e) {
            throw e;
        }
    }

    protected StringBuilder getQuery(final List<SearchCondition<SearchBean>> cond) {
        StringBuilder query = new StringBuilder();
        if (cond == null || cond.isEmpty()) {
            return query;
        }
        for (SearchCondition searchItem : cond) {
            if (!searchItem.getSearchConditions().isEmpty()) {
                query.append(getQuery(searchItem.getSearchConditions()));
                continue;
            }
            query.append("(");
            SearchBean searchBean = searchItem.getCondition();
            switch (searchItem.getConditionType()) {
                case AND:
                    query.append(searchItem.getCondition());
                    break;
                case OR:
                    query.append(searchItem.getCondition());
                    break;
                default:
            }

        }


        return query;
    }
}
