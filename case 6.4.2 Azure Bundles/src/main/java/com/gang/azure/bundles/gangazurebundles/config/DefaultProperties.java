package com.gang.azure.bundles.gangazurebundles.config;

/**
 * @Classname DefaultProperties
 * @Description TODO
 * @Date 2020/3/31 20:56
 * @Created by zengzg
 */
public class DefaultProperties {

    private static String defaultVsersion = "api-version=1.6";

    public static String parentPath = "https://graph.windows.net/${tenant}";

    public static String userList = parentPath + "/users?" + defaultVsersion;
}
