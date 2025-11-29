package com.gang.base.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * <p>
 * 用于演示 JSON 序列化和反序列化。
 * </p>
 *
 * @author zengzg
 * @since 2019/12/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** 用户名 */
    private String username;
    
    /** 年龄 */
    private int age;
}
