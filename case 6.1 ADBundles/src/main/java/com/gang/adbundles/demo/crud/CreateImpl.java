package com.gang.adbundles.demo.crud;

import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;

import java.util.Set;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 19:36
 * @Version 1.0
 **/

public class CreateImpl extends ADCreate {

    public CreateImpl(ADConnection conn, ObjectClass oclass, Set<Attribute> attrs, OperationOptions options) {
        super(conn, oclass, attrs, options);
    }
}
