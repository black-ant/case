package com.gang.study.annotation.demo.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssociationNotNullAnno implements ConstraintValidator<AssociationNotNull, String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String[] associateField = {};

    @Override
    public void initialize(AssociationNotNull constraint) {
        associateField = constraint.associationFiled();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("------> valid value is :{} <-------", value);
        logger.info("------> context is :{} <-------", context);
        logger.info("------> associateField is :{} <-------", associateField);
        return true;
    }

}
