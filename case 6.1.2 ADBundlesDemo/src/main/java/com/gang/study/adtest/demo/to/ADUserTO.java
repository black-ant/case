package com.gang.study.adtest.demo.to;

import com.gang.study.adtest.demo.utils.ADAnnotation;
import lombok.Data;

/**
 * @Classname ADUserTO
 * @Description TODO
 * @Date 2019/12/5 16:59
 * @Created by zengzg
 */
@Data
public class ADUserTO extends ADBaseTO {

    @ADAnnotation(alias = "cn")
    public String cn;

    @ADAnnotation(alias = "sn")
    public String snName;

    @ADAnnotation(alias = "givenName ")
    public String givename;

    @ADAnnotation(alias = "name")
    public String username;

    @ADAnnotation(alias = "description")
    public String description;

    @ADAnnotation(alias = "displayName")
    public String displayName;

    @ADAnnotation(alias = "sAMAccountName")
    public String sAMAccountName;

    @ADAnnotation(alias = "userPrincipalName")
    public String userPrincipalName;

    @ADAnnotation(alias = "title")
    public String title;

    @ADAnnotation(alias = "mobile")
    public String mobile;

    @ADAnnotation(alias = "ipPhone")
    public String ipPhone;

    @ADAnnotation(alias = "homePhone")
    public String homePhone;

    @ADAnnotation(alias = "countryCode")
    public String country;

    @ADAnnotation(alias = "st")
    public String province;

    @ADAnnotation(alias = "l")
    public String city;

    @ADAnnotation(alias = "streetAddress")
    public String streetAddress;

    @ADAnnotation(alias = "physicalDeliveryOfficeName")
    public String physicalDeliveryOfficeName;

    @ADAnnotation(alias = "telephoneNumber")
    public String telephoneNumber;

    @ADAnnotation(alias = "mail")
    public String mail;

    @ADAnnotation(alias = "Initials")
    public String Initials;

    @ADAnnotation(alias = "info")
    public String info;

    @ADAnnotation(alias = "postOfficeBox")
    public String postOfficeBox;

    @ADAnnotation(alias = "postalCode")
    public String postalCode;

    @ADAnnotation(alias = "company")
    public String company;

    @ADAnnotation(alias = "department")
    public String department;

    @ADAnnotation(alias = "wWWHomePage")
    public String wWWHomePage;

    @ADAnnotation(alias = "ldapGroups")
    public String[] ldapGroups;

    public ADUserTO() {
    }

    public ADUserTO(String key) {
        this.key = key;
    }
}
