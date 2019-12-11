package com.gang.study.ldap.ldap.service;

import com.gang.study.ldap.ldap.to.ADBaseTO;
import com.gang.study.ldap.ldap.to.ADConfigInfoTO;
import com.gang.study.ldap.ldap.to.AbstractConfigTO;
import com.gang.study.ldap.ldap.to.SearchTO;
import com.gang.study.ldap.ldap.utils.ADSyncUtils;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname AbstractOutput
 * @Description TODO
 * @Date 2019/12/6 19:15
 * @Created by zengzg
 */
public abstract class AbstractOutput<T extends ADBaseTO, E extends ADBaseTO> {

    @Autowired
    private ADBaseInfo adBase;
    private ObjectClass objectClass;

    public void init(AbstractConfigTO abstractConfigTO) {
        adBase = adBase.init((ADConfigInfoTO) abstractConfigTO);
        objectClass = getObjectClass();
        checkAccessToken();
    }


    public String checkAccessToken() {
        adBase.getAdConnection().test();
        return "ok";
    }

    public T create(E adObjectTO, AbstractConfigTO abstractConfigTO) {
        init(abstractConfigTO);
        adObjectTO.setKey(adBase.getAdCreate(objectClass, adObjectTO).execute().getUidValue());
        return (T) adObjectTO;
    }


    public T update(E adObjectTO, AbstractConfigTO abstractConfigTO) {
        init(abstractConfigTO);
        adBase.getAdUpdate(objectClass, adObjectTO.getKey()).update(ADSyncUtils.getAttributeInfo(adObjectTO));
        return (T) adObjectTO;
    }


    public T delete(String deleteInfo, AbstractConfigTO abstractConfigTO) {
        init(abstractConfigTO);
        adBase.getAdDelete(objectClass, deleteInfo).execute();
        return (T) new ADBaseTO(deleteInfo);
    }


    public AbstractConfigTO checkConfig() {
        return null;
    }

    //    public T get(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
    //        init(abstractConfigTO);
    //        List<ADBaseTO> list = ADSearchUtils.getTO(objectClass, adBase.getUtils(), "objectGUID", (String) searchTO.getKey(), "OU=上海派拉技术有限公司,DC=wdhacpoc,DC=com,DC=cn", adBase.getAdConnection());
    //        return list.isEmpty() ? null : (T) list.get(0);
    //    }
    //
    //    public List<ADBaseTO> search(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
    //        init(abstractConfigTO);
    //        return ADSearchUtils.searchInvoke(objectClass, adBase.getUtils(), "OU=上海派拉技术有限公司,DC=wdhacpoc,DC=com,DC=cn", adBase.getAdConnection());
    //    }

    public T list(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    public T listChild(SearchTO searchTO, AbstractConfigTO abstractConfigTO) {
        return null;
    }

    public abstract ObjectClass getObjectClass();

}
