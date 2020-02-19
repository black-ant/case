package com.gang.study.adtest.demo.output;


import com.gang.study.adtest.demo.to.ADOrgTO;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname ADCreate
 * @Description TODO
 * @Date 2019/12/2 18:04
 * @Created by zengzg
 */
@Component
public class OrgOutputImpl extends AbstractOutput<ADOrgTO, ADOrgTO> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectClass objectClass = new ObjectClass("__ORGANIZATION__");


    @Override
    public ObjectClass getObjectClass() {
        return objectClass;
    }
}

