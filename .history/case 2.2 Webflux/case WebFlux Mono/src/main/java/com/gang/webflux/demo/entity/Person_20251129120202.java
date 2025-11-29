package com.gang.webflux.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人员实体类
 * <p>
 * 用于演示 Mono 数据转换。
 * </p>
 *
 * @author zengzg
 * @since 2021/8/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    /** 姓名 */
    private String name;
    
    /** 年龄 */
    private Integer age;
}
