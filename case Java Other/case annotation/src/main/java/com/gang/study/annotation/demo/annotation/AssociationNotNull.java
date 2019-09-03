package com.gang.study.annotation.demo.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Constraint(validatedBy = AssociationNotNullAnno.class)
public @interface AssociationNotNull {

    String message() default "关联属性不合法";

    String[] associationFiled() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
