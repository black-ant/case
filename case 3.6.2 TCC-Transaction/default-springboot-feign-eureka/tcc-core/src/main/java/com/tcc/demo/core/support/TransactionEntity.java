package com.tcc.demo.core.support;

import org.mengyun.tcctransaction.api.TransactionContext;

import java.io.Serializable;

/**
 * 携带事务上下文的实体
 *
 * @author Huanghs
 * @since 2.0
 * @date 2017/12/8
 */
public class TransactionEntity<T> implements Serializable {

    private static final long serialVersionUID = -5183807142356024818L;

    /** 事务上下文 */
    private TransactionContext context;

    /** 内容体 */
    private T body;

    public TransactionEntity() {
    }

    public TransactionEntity(TransactionContext context) {
        this.context = context;
    }

    public TransactionEntity(TransactionContext context, T body) {
        this.context = context;
        this.body = body;
    }

    public TransactionContext getContext() {
        return context;
    }

    public void setContext(TransactionContext context) {
        this.context = context;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
