package com.gang.study.sso.ltpa.demo.logic;

import com.gang.study.sso.ltpa.demo.api.IAlgorithmLogic;
import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.exception.LtpaException;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LtpaAlogrithmManager
 * @Description TODO
 * @Date 2020/7/1 16:27
 * @Created by zengzg
 */
@Component
public class LtpaAlogrithmManager implements IAlgorithmLogic {

    @Override
    public String encode(UserInfo userInfo, Map<String, String> properties) {

        IAlgorithmLogic logic = createIAlgorithmLogic(properties);
        properties.putAll(logic.getLtpaProperties());

        return logic.encode(userInfo, properties);
    }

    @Override
    public UserMetadata decode(String token, Map<String, String> properties) {

        IAlgorithmLogic logic = createIAlgorithmLogic(properties);
        properties.putAll(logic.getLtpaProperties());

        return logic.decode(token, properties);
    }

    /**
     * 创建 IAlgorithmLogic
     *
     * @param properties
     * @return
     */
    public IAlgorithmLogic createIAlgorithmLogic(Map<String, String> properties) {
        IAlgorithmLogic algorithmLogic = null;
        if (properties.get("cryptType").equals("AES")) {
            algorithmLogic = new AESAlgorithmLogic();
        } else if (properties.get("cryptType").equals("DES")) {
            algorithmLogic = new DESAlgorithmLogic();
        } else {
            throw new LtpaException("No Crypt Type");
        }
        return algorithmLogic;
    }

    @Override
    public Map<String, String> getLtpaProperties() {
        Map<String, String> proMap = new HashMap<>();
        proMap.put("expiredTime", "10");
        proMap.put("cryptType", "DES");
        return proMap;
    }
}
