package com.search.elastic.to;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Response
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
public class ResponseTO<E> {

    private final static String SUCCESS_CODE = "200";
    private String code;

    private E data;

    private String dataType;

    private final Map<String, Object> info = new HashMap<>();

    public static <T> ResponseTO<T> commonResponse(final T entity) {
        return
                new ResponseTO.Builder<T>()
                        .data(entity)
                        .info(ResponseType.SUCCESS)
                        .code(SUCCESS_CODE)
                        .dataType(entity == null ? "" : entity.getClass().getName())
                        .build();
    }

    public ResponseTO addInfo(String key, Object value) {
        this.info.put(key, value);
        return this;
    }

    public static class Builder<E> {

        protected ResponseTO instance;

        protected ResponseTO<E> getInstance() {
            if (instance == null) {
                instance = new ResponseTO<E>();
            }
            return instance;
        }

        public Builder<E> data(E data) {
            getInstance().setData(data);
            return this;
        }

        public Builder<E> code(String value) {
            getInstance().setCode(value);
            return this;
        }

        public ResponseTO<E> build() {
            return getInstance();
        }


        public Builder<E> info(ResponseType responseType) {
            getInstance().getInfo().put("code", responseType.getCode());
            getInstance().getInfo().put("info", responseType.getInfo());
            return this;
        }

        public Builder<E> dataType(String dataType) {
            getInstance().setDataType(dataType);
            return this;
        }

        public Builder<E> info(String key, String value) {
            getInstance().getInfo().put(key, value);
            return this;
        }

    }

    public static enum ResponseType {

        SUCCESS("1", "success"),
        FAILURE("-1", "failure");

        private String code;
        private String info;

        ResponseType(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Map<String, Object> getInfo() {
        return info;
    }
}


