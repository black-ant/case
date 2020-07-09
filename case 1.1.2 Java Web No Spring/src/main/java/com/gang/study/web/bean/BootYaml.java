//package com.gang.study.web.bean;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
//import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
//import org.yaml.snakeyaml.Yaml;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.LinkedHashMap;
//import java.util.List;
//
///**
// * @Classname BootYaml
// * @Description TODO
// * @Date 2020/7/9 17:12
// * @Created by zengzg
// */
//public class BootYaml extends Yaml {
//    /**
//     * ��������·���ļ�ֵ
//     */
//    private String active;
//    /**
//     * ����yml�ļ�ֵ
//     */
//    private String include;
//    /**
//     * �����ļ���ǰ׺
//     */
//    private String prefix;
//
//    /**
//     * <p>�������ƣ�����application.ymlת��ΪLinkedHashMap.</p>
//     * <p>��ϸ�����������spring.profiles.active���õ����ú�spring.profiles.include������ļ�.</p>
//     * <p>����ʱ�䣺2019-07-10 17:39:38</p>
//     * <p>�������ߣ�������</p>
//     * <p>�޸ļ�¼��</p>
//     *
//     * @param path application.yml
//     * @return the linked hash map
//     * @author "lixingwu"
//     */
//    public LinkedHashMap loadAs(String path) {
//        // ���һ��map�������õ����ã�������ļ��������
//        LinkedHashMap<String, Object> mapAll = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> mainMap = yml2Map(path);
//        // ��ȡ���õ�����
//        Object active = mainMap.get(this.active);
//        if (!Tools.isBlank(active)) {
//            mapAll.putAll(yml2Map(StrUtil.format("{}-{}.yml", this.prefix, active)));
//        }
//        // ���������yml
//        Object include = mainMap.get(this.include);
//        // include��ʹ�ö��ŷָ����ģ���Ҫ�и�һ��
//        List<String> split = StrSpliter.split(Convert.toStr(include), StrUtil.C_COMMA, true, true);
//        for (String inc : split) {
//            mapAll.putAll(yml2Map(StrUtil.format("{}-{}.yml", this.prefix, inc)));
//        }
//        // �����ø�����������
//        mapAll.putAll(mainMap);
//        // ��mapת��Ϊ�ַ���
//        String mapString = MapUtil.joinIgnoreNull(mapAll, "\n", "=");
//        // �ٰ�map�ַ���ת��ΪyamlStr�ַ���
//        String yamlStr = properties2YamlStr(mapString);
//        // ʹ��Yaml����LinkedHashMap
//        return super.loadAs(yamlStr, LinkedHashMap.class);
//    }
//
//    /**
//     * <p>�������ƣ�Yml ��ʽת LinkedHashMap.</p>
//     * <p>��ϸ������ת���� https://www.cnblogs.com/xujingyang/p/10613206.html .</p>
//     * <p>����ʱ�䣺2019-07-10 09:30:19</p>
//     * <p>�������ߣ�������</p>
//     * <p>�޸ļ�¼��</p>
//     *
//     * @param path Yml·��
//     * @author "lixingwu"
//     */
//    public LinkedHashMap<String, Object> yml2Map(String path) {
//        final String dot = ".";
//        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//        ClassPathResource resource = new ClassPathResource(path);
//        // �ļ������ڣ��ÿ�
//        if (Tools.isBlank(resource)) {
//            return map;
//        }
//        BufferedReader reader = resource.getReader(Charset.defaultCharset());
//        try {
//            YAMLFactory yamlFactory = new YAMLFactory();
//            YAMLParser parser = yamlFactory.createParser(reader);
//            StringBuilder key = new StringBuilder();
//            String value;
//            JsonToken token = parser.nextToken();
//            while (token != null) {
//                if (!JsonToken.START_OBJECT.equals(token)) {
//                    if (JsonToken.FIELD_NAME.equals(token)) {
//                        if (key.length() > 0) {
//                            key.append(dot);
//                        }
//                        key.append(parser.getCurrentName());
//
//                        token = parser.nextToken();
//                        if (JsonToken.START_OBJECT.equals(token)) {
//                            continue;
//                        }
//                        value = parser.getText();
//                        map.put(key.toString(), value);
//
//                        int dotOffset = key.lastIndexOf(dot);
//                        if (dotOffset > 0) {
//                            key = new StringBuilder(key.substring(0, dotOffset));
//                        }
//                    } else if (JsonToken.END_OBJECT.equals(token)) {
//                        int dotOffset = key.lastIndexOf(dot);
//                        if (dotOffset > 0) {
//                            key = new StringBuilder(key.substring(0, dotOffset));
//                        } else {
//                            key = new StringBuilder();
//                        }
//                    }
//                }
//                token = parser.nextToken();
//            }
//            parser.close();
//            return map;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * <p>�������ƣ�Properties����ת��Ϊyaml����.</p>
//     * <p>��ϸ������.</p>
//     * <p>����ʱ�䣺2019-07-10 15:06:48</p>
//     * <p>�������ߣ�������</p>
//     * <p>�޸ļ�¼��</p>
//     *
//     * @param content Properties����
//     * @return the string
//     * @author "lixingwu"
//     */
//    public String properties2YamlStr(String content) {
//        // ��ʱ����yml
//        String filePath = FileUtil.getTmpDirPath() + "/temp.yml";
//
//        JsonParser parser;
//        JavaPropsFactory factory = new JavaPropsFactory();
//        try {
//            parser = factory.createParser(content);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            YAMLFactory yamlFactory = new YAMLFactory();
//            YAMLGenerator generator = yamlFactory.createGenerator(FileUtil.getOutputStream(filePath));
//            JsonToken token = parser.nextToken();
//            while (token != null) {
//                if (JsonToken.START_OBJECT.equals(token)) {
//                    generator.writeStartObject();
//                } else if (JsonToken.FIELD_NAME.equals(token)) {
//                    generator.writeFieldName(parser.getCurrentName());
//                } else if (JsonToken.VALUE_STRING.equals(token)) {
//                    generator.writeString(parser.getText());
//                } else if (JsonToken.END_OBJECT.equals(token)) {
//                    generator.writeEndObject();
//                }
//                token = parser.nextToken();
//            }
//            parser.close();
//            generator.flush();
//            generator.close();
//            // ��ȡ��ʱ����yml������
//            String ymlContent = FileUtil.readUtf8String(filePath);
//            // ɾ����ʱ����yml
//            FileUtil.del(filePath);
//            return ymlContent;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String getActive() {
//        return active;
//    }
//
//    public void setActive(String active) {
//        this.active = active;
//    }
//
//    public String getInclude() {
//        return include;
//    }
//
//    public void setInclude(String include) {
//        this.include = include;
//    }
//
//    public String getPrefix() {
//        return prefix;
//    }
//
//    public void setPrefix(String prefix) {
//        this.prefix = prefix;
//    }
//}
