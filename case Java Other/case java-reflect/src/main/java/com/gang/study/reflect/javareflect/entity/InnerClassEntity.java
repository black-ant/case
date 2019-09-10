package com.gang.study.reflect.javareflect.entity;

import lombok.Data;

@Data
public class InnerClassEntity {

    private Inner inner;

    private String userType;

    public InnerClassEntity(Inner inner, String userType) {
        this.inner = inner;
        this.userType = userType;
    }

    public InnerClassEntity() {
        super();
    }

    @Data
    public static class Inner {

        public Inner() {
            super();
        }

        public Inner(String username, Integer age) {
            this.username = username;
            this.age = age;
        }

        private String username;

        private Integer age;

    }
}
