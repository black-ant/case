//package com.gang.study.web.utils;
//
//import com.gang.study.web.bean.BootYaml;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//
///**
// * @Classname YmlPropUtils
// * @Description TODO
// * @Date 2020/7/9 17:12
// * @Created by zengzg
// */
//public class YmlPropUtils {
//    private LinkedHashMap prop;
//    private static YmlPropUtils ymlPropUtils = new YmlPropUtils();
//
//    private YmlPropUtils() {
//        BootYaml yaml = new BootYaml();
//        yaml.setActive("spring.profiles.active");
//        yaml.setInclude("spring.profiles.include");
//        yaml.setPrefix("application");
//        prop = yaml.loadAs("application.yml");
//    }
//
//    /**
//     * 获取单例
//     *
//     * @return YmlPropUtils
//     */
//    public static YmlPropUtils getInstance() {
//        if (ymlPropUtils == null) {
//            ymlPropUtils = new YmlPropUtils();
//        }
//        return ymlPropUtils;
//    }
//
//    /**
//     * 根据属性名读取值
//     * 先去主配置查询，如果查询不到，就去启用配置查询
//     *
//     * @param name 名称
//     */
//    public Object getProperty(String name) {
//        LinkedHashMap param = prop;
//        List<String> split = StringUtils.split(name, StrUtil.C_DOT, true, true);
//        for (int i = 0; i < split.size(); i++) {
//            if (i == split.size() - 1) {
//                return param.get(split.get(i));
//            }
//            param = Convert.convert(LinkedHashMap.class, param.get(split.get(i)));
//        }
//        return null;
//    }
//}
