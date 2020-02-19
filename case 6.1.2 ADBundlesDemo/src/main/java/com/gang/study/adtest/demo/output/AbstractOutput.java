package com.gang.study.adtest.demo.output;

import com.gang.study.adtest.demo.to.ADBaseTO;
import com.gang.study.adtest.demo.to.ADConfigInfoTO;
import com.gang.study.adtest.demo.to.DeleteInfo;
import com.gang.study.adtest.demo.to.SearchTO;
import com.gang.study.adtest.demo.utils.ADSearchUtils;
import com.gang.study.adtest.demo.utils.ADSyncUtils;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    public void init(ADConfigInfoTO abstractConfigTO) {
        adBase = adBase.init(abstractConfigTO);
        objectClass = getObjectClass();
    }


    public String checkAccessToken() {
        adBase.getAdConnection().test();
        return "ok";
    }

    public T create(E adObjectTO, ADConfigInfoTO abstractConfigTO) {
        init(abstractConfigTO);
        adObjectTO.setKey(adBase.getAdCreate(objectClass, adObjectTO).create().getUidValue());
        return (T) adObjectTO;
    }


    public T update(E adObjectTO, ADConfigInfoTO abstractConfigTO) {
        init(abstractConfigTO);
        adBase.getAdUpdate(objectClass, adObjectTO.getKey()).update(ADSyncUtils.getAttributeInfo(adObjectTO));
        return (T) adObjectTO;
    }


    public T delete(DeleteInfo deleteInfo, ADConfigInfoTO abstractConfigTO) {
        init(abstractConfigTO);
        adBase.getAdDelete(objectClass, (String) deleteInfo.getDeleteid()).delete();
        return (T) new ADBaseTO((String) deleteInfo.getDeleteid());
    }


    public ADConfigInfoTO checkConfig() {
        return null;
    }

    public T get(SearchTO searchTO, ADConfigInfoTO abstractConfigTO) {
        init(abstractConfigTO);
        List<ADBaseTO> list = ADSearchUtils.getTO(objectClass, adBase.getUtils(), "objectGUID",
                (String) searchTO.getKey(), "OU=上海派拉技术有限公司,DC=wdhacpoc,DC=com,DC=cn", adBase.getAdConnection());
        return list.isEmpty() ? null : (T) list.get(0);
    }

    public List<ADBaseTO> search(SearchTO searchTO, ADConfigInfoTO abstractConfigTO) {
        init(abstractConfigTO);
        return ADSearchUtils.searchInvoke(objectClass, adBase.getUtils(), "OU=上海派拉技术有限公司,DC=wdhacpoc,DC=com,DC=cn",
                adBase.getAdConnection());
    }

    public T list(SearchTO searchTO, ADConfigInfoTO abstractConfigTO) {
        return null;
    }

    public T listChild(SearchTO searchTO, ADConfigInfoTO abstractConfigTO) {
        return null;
    }

    public abstract ObjectClass getObjectClass();
}
