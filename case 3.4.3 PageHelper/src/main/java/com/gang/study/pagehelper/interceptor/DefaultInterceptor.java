package com.gang.study.pagehelper.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Classname DefaultInterceptor
 * @Description TODO
 *
 * 1.Executor：拦截执行器的方法。
 * 2.ParameterHandler：拦截参数的处理。
 * 3.ResultHandler：拦截结果集的处理。
 * 4.StatementHandler：拦截Sql语法构建的处理。
 *
 * @Date 2021/5/15
 * @Created by zengzg
 */
@Intercepts({//注意看这个大花括号，也就这说这里可以定义多个@Signature对多个地方拦截，都用这个拦截器
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DefaultInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Intercepts：标识该类是一个拦截器；
     * @Signature：指明自定义拦截器需要拦截哪一个类型，哪一个方法；
     * - type：上述四种类型中的一种；
     * - method：对应接口中的哪类方法（因为可能存在重载方法）；
     * - args：对应哪一个方法的入参；
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("------> this is in intercept <-------");
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        Object obj = boundSql.getParameterObject();
        String sql = boundSql.getSql();
        return invocation.proceed();
    }

    /**
     * 是否要进行拦截，然后做出决定是否生成一个代理
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        logger.info("------> this is in plugin <-------");
        return Plugin.wrap(o, this);
    }

    /**
     * 拦截器需要一些变量对象，而且这个对象是支持可配置的
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        logger.info("------> this is in setProperties <-------");
    }
}
