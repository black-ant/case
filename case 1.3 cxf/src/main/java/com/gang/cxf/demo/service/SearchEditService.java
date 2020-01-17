package com.gang.cxf.demo.service;

import com.gang.cxf.demo.module.*;
import org.apache.cxf.jaxrs.ext.search.*;
import org.apache.cxf.message.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchEditService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void exchange() {
//        getSearchCond("(username==gang)");

//        getSearchCond("username==gang,age==777");
        getSearchCond("(param1==*00000*;param2==*11111*),(param3==*2222*)");
//        getSearchCond("foo==123.4,(bar==asadf*;baz=lt=20.0)");
    }


    protected void getSearchCond(final String fiql) {
        try {
            SearchContext searchContext = new SearchContextProvider().createContext(new MessageImpl());

            SearchCondVisitor visitor = new SearchCondVisitor();

            SearchCondition<SearchBean> sc = searchContext.getCondition(fiql, SearchBean.class);

            sc.accept(visitor);

            SearchCond searchCond = visitor.getQuery();
            logger.info("------> this is :{} <-------", sc);
            logger.info("------> searchCond is :{} <-------", searchCond);
            List<Object> parameters = Collections.synchronizedList(new ArrayList<>());
            StringBuilder queryString = getQuery(searchCond, parameters);
            logger.info("------> this fiql :{} <-------", queryString);

        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 递归生成 SQL 查询 语句
     *
     * @param cond
     * @return
     */
    protected StringBuilder getQuery1(final List<SearchCondition<SearchBean>> cond) {
        StringBuilder query = new StringBuilder();
        if (cond == null || cond.isEmpty()) {
            return query;
        }
        for (SearchCondition searchItem : cond) {
            if (null != searchItem.getSearchConditions() && !searchItem.getSearchConditions().isEmpty()) {
//                query.append(getQuery(searchItem.getSearchConditions()));
                continue;
            }
            query.append("(");
            SearchBean searchBean = (SearchBean) searchItem.getCondition();
            switch (searchItem.getConditionType()) {
                case EQUALS:
                    String key = searchBean.getKeySet().iterator().next();
                    query.append(key).append("=").append(searchBean.get(key));
                    break;
                case OR:
                    query.append(searchItem.getCondition());
                    break;
                default:
            }
            query.append(")");

        }


        return query;
    }
}
