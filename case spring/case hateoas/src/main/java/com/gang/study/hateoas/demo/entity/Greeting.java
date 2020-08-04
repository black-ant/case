package com.gang.study.hateoas.demo.entity;

/**
 * @Classname Greeting
 * @Description TODO
 * @Date 2020/8/4 17:56
 * @Created by zengzg
 */
public class Greeting extends ResourceSupport {

    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
