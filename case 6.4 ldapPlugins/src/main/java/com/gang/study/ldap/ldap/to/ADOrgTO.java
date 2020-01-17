package com.gang.study.ldap.ldap.to;

import com.gang.study.ldap.ldap.utils.ADAnnotation;
import lombok.Data;

/**
 * @Classname ADOrgTO
 * @Description TODO
 * @Date 2019/12/5 16:58
 * @Created by zengzg
 */
@Data
public class ADOrgTO extends ADBaseTO {

    @ADAnnotation(alias = "cn")
    public String cn;

    @ADAnnotation(alias = "description")
    public String description;

    @ADAnnotation(alias = "ou")
    public String ou;

    @ADAnnotation(alias = "country")
    public String country;

    @ADAnnotation(alias = "city")
    public String city;

    @ADAnnotation(alias = "streetAddress")
    public String streetAddress;

    public ADOrgTO() {
    }

    public ADOrgTO(String key) {
        this.key = key;
    }
}
