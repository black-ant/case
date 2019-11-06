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
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    private SearchCond visitPrimitive(final SearchCondition<SearchBean> sc) {
        String name = getRealPropertyName(sc.getStatement().getProperty());
        Optional<SpecialAttr> specialAttrName = SpecialAttr.fromString(name);

        String value = null;
        try {
            value = SearchUtils.toSqlWildcardString(URLDecoder.decode(sc.getStatement().getValue().toString(), StandardCharsets.UTF_8.name()), false).replaceAll("\\\\_", "_");

            // see PARAIDM-1321
            if (TIMEZONE.matcher(value).matches()) {
                char[] valueAsArray = value.toCharArray();
                valueAsArray[valueAsArray.length - 5] = '+';
                value = new String(valueAsArray);
            }

            // TODO default search data
            if (SEARCH_DEFAULT_DATA.matcher(value).matches()) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                value = sdf.format(date);
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("While decoding " + sc.getStatement().getValue(), e);
        }
        Optional<SpecialAttr> specialAttrValue = SpecialAttr.fromString(value);

        AttributeCond attributeCond = createAttributeCond(name);
        attributeCond.setExpression(value);

        ConditionType ct = sc.getConditionType();
        if (sc instanceof JobFiqlSearchCondition && sc.getConditionType() == ConditionType.CUSTOM) {
            JobFiqlSearchCondition<SearchBean> sfsc = (JobFiqlSearchCondition<SearchBean>) sc;
            if (JobFiqlParser.IEQ.equals(sfsc.getOperator())) {
                ct = ConditionType.EQUALS;
            } else if (JobFiqlParser.NIEQ.equals(sfsc.getOperator())) {
                ct = ConditionType.NOT_EQUALS;
            } else {
                throw new IllegalArgumentException(String.format("Condition type %s is not supported", sfsc.getOperator()));
            }
        }

        SearchCond leaf;
        AttributeCond.Type type;
        switch (ct) {
            case EQUALS:
            case NOT_EQUALS:

                if (specialAttrValue.isPresent() && specialAttrValue.get() == SpecialAttr.NULL) {
                    type = AttributeCond.Type.ISNULL;

                    attributeCond.setExpression(null);
                } else if (value.indexOf('%') == -1) {
                    type = (sc.getConditionType() == ConditionType.CUSTOM
                            ? AttributeCond.Type.IEQ
                            : AttributeCond.Type.EQ);
                } else {
                    type = (sc.getConditionType() == ConditionType.CUSTOM
                            ? AttributeCond.Type.ILIKE
                            : AttributeCond.Type.LIKE);
                }
                if (!specialAttrName.isPresent()) {
                    attributeCond.setType(type);
                    leaf = SearchCond.getLeafCond(attributeCond);
                } else {
                    switch (specialAttrName.get()) {
                        case TYPE:
                            AnyTypeCond typeCond = new AnyTypeCond();
                            typeCond.setAnyTypeKey(value);
                            leaf = SearchCond.getLeafCond(typeCond);
                            break;

                        default:
                            throw new IllegalArgumentException(String.format("Special attr name %s is not supported", specialAttrName));
                    }
                }
                if (ct == ConditionType.NOT_EQUALS) {
                    if (leaf.getAttributeCond() != null && leaf.getAttributeCond().getType() == AttributeCond.Type.ISNULL) {

                        leaf.getAttributeCond().setType(AttributeCond.Type.ISNOTNULL);
                    } else if (leaf.getAnyCond() != null && leaf.getAnyCond().getType() == AttributeCond.Type.ISNULL) {

                        leaf.getAnyCond().setType(AttributeCond.Type.ISNOTNULL);
                    } else {
                        leaf = SearchCond.getNotLeafCond(leaf);
                    }
                }
                break;

            case GREATER_OR_EQUALS:
                attributeCond.setType(AttributeCond.Type.GE);
                leaf = SearchCond.getLeafCond(attributeCond);
                break;

            case GREATER_THAN:
                attributeCond.setType(AttributeCond.Type.GT);
                leaf = SearchCond.getLeafCond(attributeCond);
                break;

            case LESS_OR_EQUALS:
                attributeCond.setType(AttributeCond.Type.LE);
                leaf = SearchCond.getLeafCond(attributeCond);
                break;

            case LESS_THAN:
                attributeCond.setType(AttributeCond.Type.LT);
                leaf = SearchCond.getLeafCond(attributeCond);
                break;

            default:
                throw new IllegalArgumentException(String.format("Condition type %s is not supported", ct.name()));
        }

        if (leaf.getAttributeCond() != null && (leaf.getAttributeCond().getType() == AttributeCond.Type.ISNULL || leaf.getAttributeCond().getType() == AttributeCond.Type.ISNOTNULL) && leaf.getAttributeCond().getExpression() == null) {

            AnyCond tokenCond = new AnyCond();
            tokenCond.setSchema(leaf.getAttributeCond().getSchema());
            tokenCond.setType(leaf.getAttributeCond().getType());
            tokenCond.setExpression(null);
            leaf = SearchCond.getLeafCond(tokenCond);
        }

        return leaf;
    }

    private SearchCond visitCompount(final SearchCondition<SearchBean> sc) {
        List<SearchCond> searchConds = new ArrayList<>();
        sc.getSearchConditions().forEach(searchCondition -> {
            searchConds.add(searchCondition.getStatement() == null
                    ? visitCompount(searchCondition)
                    : visitPrimitive(searchCondition));
        });

        SearchCond compound;
        switch (sc.getConditionType()) {
            case AND:
                compound = SearchCond.getAndCond(searchConds);
                break;

            case OR:
                compound = SearchCond.getOrCond(searchConds);
                break;

            default:
                throw new IllegalArgumentException(String.format("Condition type %s is not supported", sc.getConditionType().name()));
        }

        return compound;
    }

    private AttributeCond createAttributeCond(final String schema) {
        // --> 此处判断是否使用 scheme
//        Pair<Boolean, SchemaSort> contains = SearchableFields.contains(schema, entityTOClass);
//        AttributeCond attributeCond = contains.getLeft()
//                ? new AnyCond(contains.getRight())
//                : new AttributeCond();
        AttributeCond attributeCond = new AttributeCond();
        attributeCond.setSchema(schema);
        return attributeCond;
    }

    @Override
    public void visit(final SearchCondition<SearchBean> sc) {
        logger.info("------> this is ok <-------");
        searchCond = sc.getStatement() == null
                ? visitCompount(sc)
                : visitPrimitive(sc);
    }

    @Override
    public SearchCond getQuery() {
        return searchCond;
    }

}

