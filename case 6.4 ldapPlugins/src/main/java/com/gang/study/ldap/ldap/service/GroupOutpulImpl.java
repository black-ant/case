package com.gang.study.ldap.ldap.service;

import com.gang.study.ldap.ldap.to.ADGroupTO;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.springframework.stereotype.Component;

/**
 * @Classname GroupOutpulImpl
 * @Description TODO
 * @Date 2019/12/5 13:40
 * @Created by zengzg
 */
@Component
public class GroupOutpulImpl extends AbstractOutput<ADGroupTO, ADGroupTO> {

    private ObjectClass objectClass = ObjectClass.GROUP;

    @Override
    public ObjectClass getObjectClass() {
        return objectClass;
    }
}
