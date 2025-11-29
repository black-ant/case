package com.gang.study.freemarker.demo.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户传输对象
 * <p>
 * 用于在 Controller 和 View 之间传递用户数据。
 * </p>
 *
 * @author zengzg
 * @since 2021/1/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTO {

    /** 用户名 */
    private String username;
    
    /** 邮箱 */
    private String email;
    
    /** 年龄 */
    private Integer age;
}
