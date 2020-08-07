package com.gang.study.ldap.demo.exception;

/**
 * @Classname LdapException
 * @Description TODO
 * @Date 2020/8/7 22:07
 * @Created by zengzg
 */
public class LdapException extends RuntimeException {

    public LdapException() {
        super();
    }

    public LdapException(String message) {
        super(message);
    }
}
