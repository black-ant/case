package com.mybatistest.demo.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Classname DefaultInterceptor
 * @Description TODO
 * @Date 2021/5/23
 * @Created by zengzg
 */
@Intercepts(
        {@org.apache.ibatis.plugin.Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DefaultInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("------> this is in intercept <-------");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        logger.info("------> this is in plugin <-------");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        logger.info("------> this is in setProperties <-------");
    }
}
