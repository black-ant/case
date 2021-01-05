package com.gang.azure.bundles.gangazurebundles.utils;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;

import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Classname JexlUtils
 * @Description TODO
 * @Date 2020/3/31 21:16
 * @Created by zengzg
 */
public final class JexlUtils {

    private static final Logger LOG = Logger.getLogger("JexlUtils");

    private static JexlEngine JEXL_ENGINE;

    private static JexlEngine getEngine() {
        synchronized (LOG) {
            if (JEXL_ENGINE == null) {
                JEXL_ENGINE = new JexlBuilder().cache(512).silent(false).strict(false).create();
            }
        }

        return JEXL_ENGINE;
    }

    public static JxltEngine newJxltEngine() {
        return getEngine().createJxltEngine(false);
    }

    public static boolean isExpressionValid(final String expression) {
        boolean result;
        try {
            getEngine().createExpression(expression);
            result = true;
        } catch (JexlException e) {
            //            LOG.error("Invalid jexl expression: " + expression, e);
            result = false;
        }

        return result;
    }

    public static String evaluate(final String template, final Map<String, Object> jexlVars) {
        StringWriter writer = new StringWriter();
        JexlUtils.newJxltEngine().createTemplate(template).evaluate(new MapContext(jexlVars), writer);
        return writer.toString();
    }

    private JexlUtils() {
    }
}
