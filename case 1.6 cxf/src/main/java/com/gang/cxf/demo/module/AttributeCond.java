package com.gang.cxf.demo.module;

/**
 * @Classname AttributeCond
 * @Description TODO
 * @Date 2019/10/31 10:56
 * @Created by ant-black 1016930479@qq.com
 */
public class AttributeCond extends AbstractSearchCond {

    private static final long serialVersionUID = 3275277728404021417L;

    public enum Type {

        LIKE,
        ILIKE,
        EQ,
        IEQ,
        GT,
        LT,
        GE,
        LE,
        ISNULL,
        ISNOTNULL

    }

    private Type type;

    private String schema;

    private String expression;

    public AttributeCond() {
        super();
    }

    public AttributeCond(final Type conditionType) {
        super();
        this.type = conditionType;
    }

    public final String getExpression() {
        return expression;
    }

    public final void setExpression(final String conditionExpression) {
        this.expression = conditionExpression;
    }

    public final String getSchema() {
        return schema;
    }

    public final void setSchema(final String conditionSchema) {
        this.schema = conditionSchema;
    }

    public final Type getType() {
        return type;
    }

    public final void setType(final Type conditionType) {
        this.type = conditionType;
    }

    @Override
    public final boolean isValid() {
        return type != null && schema != null && (type == Type.ISNULL || type == Type.ISNOTNULL || expression != null);
    }
}

