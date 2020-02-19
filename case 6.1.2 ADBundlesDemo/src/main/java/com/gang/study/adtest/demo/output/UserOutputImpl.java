package com.gang.study.adtest.demo.output;

import com.gang.study.adtest.demo.to.ADUserTO;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Classname UserOutputImpl
 * @Description TODO
 * @Date 2019/12/5 13:40
 * @Created by zengzg
 */
@Component
public class UserOutputImpl extends AbstractOutput<ADUserTO, ADUserTO> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectClass objectClass = ObjectClass.ACCOUNT;


    @Override
    public ObjectClass getObjectClass() {
        return objectClass;
    }

}
