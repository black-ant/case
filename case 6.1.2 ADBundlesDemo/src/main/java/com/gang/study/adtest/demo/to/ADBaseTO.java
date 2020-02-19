package com.gang.study.adtest.demo.to;

import com.gang.study.adtest.demo.utils.ADAnnotation;
import lombok.Data;

/**
 * @Classname ADBaseTO
 * @Description TODO
 * @Date 2019/12/5 18:48
 * @Created by zengzg
 */
@Data
public class ADBaseTO extends AbstractBaseBean {

    public String key;

    @ADAnnotation(alias = "__NAME__")
    public String name;

    @ADAnnotation(alias = "__ORG__")
    public String org;

    public ADBaseTO() {
    }

    public ADBaseTO(String key) {
        this.key = key;
    }
}
