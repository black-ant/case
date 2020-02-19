package com.gang.study.adtest.demo.to;

import com.gang.study.adtest.demo.utils.ADAnnotation;
import lombok.Data;

/**
 * @Classname ADGroupTO
 * @Description TODO
 * @Date 2019/12/5 16:59
 * @Created by zengzg
 */
@Data
public class ADGroupTO extends ADBaseTO {

    @ADAnnotation(alias = "cn")
    public String cn;

    @ADAnnotation(alias = "displayName")
    public String displayName;

    @ADAnnotation(alias = "sAMAccountName")
    public String sAMAccountName;

    @ADAnnotation(alias = "streetAddress")
    public String streetAddress;

    @ADAnnotation(alias = "description")
    public String description;

    @ADAnnotation(alias = "mail")
    public String mail;

    @ADAnnotation(alias = "info")
    public String info;

    public ADGroupTO() {
    }

    public ADGroupTO(String key) {
        this.key = key;
    }

}
