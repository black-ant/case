package com.gang.cxf.demo.module;

import java.util.List;

/**
 * @Classname SearchCond
 * @Description TODO
 * @Date 2019/10/31 10:46
 * @Created by ant-black 1016930479@qq.com
 */
public class SearchCond extends AbstractSearchCond {

    private static final long serialVersionUID = 661560782247499526L;

    public enum Type {

        LEAF,
        NOT_LEAF,
        AND,
        OR

    }

    private Type type;

    private AnyTypeCond anyTypeCond;

    private AnyCond anyCond;

    private AttributeCond attributeCond;

    private SearchCond leftSearchCond;

    private SearchCond rightSearchCond;

    public static SearchCond getLeafCond(final AnyTypeCond anyTypeCond) {
        SearchCond nodeCond = new SearchCond();

        nodeCond.type = Type.LEAF;
        nodeCond.anyTypeCond = anyTypeCond;

        return nodeCond;
    }

    public static SearchCond getLeafCond(final AttributeCond attributeCond) {
        SearchCond nodeCond = new SearchCond();

        nodeCond.type = Type.LEAF;
        if (attributeCond instanceof AnyCond) {
            nodeCond.anyCond = (AnyCond) attributeCond;
        } else {
            nodeCond.attributeCond = attributeCond;
        }

        return nodeCond;
    }


    public static SearchCond getNotLeafCond(final AttributeCond attributeCond) {
        SearchCond nodeCond = getLeafCond(attributeCond);
        nodeCond.type = Type.NOT_LEAF;
        return nodeCond;
    }


    public static SearchCond getNotLeafCond(final SearchCond nodeCond) {
        nodeCond.type = Type.NOT_LEAF;
        return nodeCond;
    }

    public static SearchCond getAndCond(final SearchCond leftCond, final SearchCond rightCond) {
        SearchCond nodeCond = new SearchCond();

        nodeCond.type = Type.AND;
        nodeCond.leftSearchCond = leftCond;
        nodeCond.rightSearchCond = rightCond;

        return nodeCond;
    }

    public static SearchCond getAndCond(final List<SearchCond> conditions) {
        if (conditions.size() == 1) {
            return conditions.get(0);
        } else if (conditions.size() > 2) {
            SearchCond removed = conditions.remove(0);
            return getAndCond(removed, getAndCond(conditions));
        } else {
            return getAndCond(conditions.get(0), conditions.get(1));
        }
    }

    public static SearchCond getOrCond(final SearchCond leftCond, final SearchCond rightCond) {
        SearchCond nodeCond = new SearchCond();

        nodeCond.type = Type.OR;
        nodeCond.leftSearchCond = leftCond;
        nodeCond.rightSearchCond = rightCond;

        return nodeCond;
    }


    public static SearchCond getOrCond(final List<SearchCond> conditions) {
        if (conditions.size() == 1) {
            return conditions.get(0);
        } else if (conditions.size() > 2) {
            SearchCond removed = conditions.remove(0);
            return getOrCond(removed, getOrCond(conditions));
        } else {
            return getOrCond(conditions.get(0), conditions.get(1));
        }
    }

    public AnyTypeCond getAnyTypeCond() {
        return anyTypeCond;
    }

    public void setAnyTypeCond(final AnyTypeCond anyTypeCond) {
        this.anyTypeCond = anyTypeCond;
    }

    /**
     * Not a simple getter: recursively scans the search condition tree.
     *
     * @return the AnyType key or {@code NULL} if no type condition was found
     */
    public String hasAnyTypeCond() {
        String anyTypeName = null;

        if (type == null) {
            return anyTypeName;
        }

        switch (type) {
            case LEAF:
            case NOT_LEAF:
                if (anyTypeCond != null) {
                    anyTypeName = anyTypeCond.getAnyTypeKey();
                }
                break;

            case AND:
            case OR:
                if (leftSearchCond != null) {
                    anyTypeName = leftSearchCond.hasAnyTypeCond();
                }
                if (anyTypeName == null && rightSearchCond != null) {
                    anyTypeName = rightSearchCond.hasAnyTypeCond();
                }
                break;

            default:
        }

        return anyTypeName;
    }

    public AnyCond getAnyCond() {
        return anyCond;
    }

    public AttributeCond getAttributeCond() {
        return attributeCond;
    }

    public SearchCond getLeftSearchCond() {
        return leftSearchCond;
    }

    public SearchCond getRightSearchCond() {
        return rightSearchCond;
    }

    public Type getType() {
        return type;
    }


    @Override
    public boolean isValid() {
        boolean isValid = false;

        if (type == null) {
            return isValid;
        }

        switch (type) {
            case LEAF:
            case NOT_LEAF:
                isValid = (anyTypeCond != null || anyCond != null || attributeCond != null) && (anyTypeCond == null || anyTypeCond.isValid()) && (anyCond == null || anyCond.isValid()) && (attributeCond == null || attributeCond.isValid());
                break;

            case AND:
            case OR:
                isValid = (leftSearchCond == null || rightSearchCond == null)
                        ? false
                        : leftSearchCond.isValid() && rightSearchCond.isValid();
                break;

            default:
        }

        return isValid;
    }
}
