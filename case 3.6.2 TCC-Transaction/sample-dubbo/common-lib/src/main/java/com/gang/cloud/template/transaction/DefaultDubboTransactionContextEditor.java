package com.gang.cloud.template.transaction;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.mengyun.tcctransaction.dubbo.constants.TransactionContextConstants;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;

import java.lang.reflect.Method;

/**
 * @Classname DefaultDubboTransactionContextEditor
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
public class DefaultDubboTransactionContextEditor extends DubboTransactionContextEditor {

    @Override
    public TransactionContext get(Object target, Method method, Object[] args) {

        String context = RpcContext.getContext().getAttachment(TransactionContextConstants.TRANSACTION_CONTEXT);

        if (StringUtils.isNotEmpty(context)) {
            return JSON.parseObject(context, TransactionContext.class);
        }

        return null;
    }

    @Override
    public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

        RpcContext.getContext().setAttachment(TransactionContextConstants.TRANSACTION_CONTEXT, JSON.toJSONString(transactionContext));
    }
}
