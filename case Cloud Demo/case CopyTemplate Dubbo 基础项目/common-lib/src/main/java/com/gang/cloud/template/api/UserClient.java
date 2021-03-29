package com.gang.cloud.template.api;

import com.gang.cloud.template.to.CommonProductTO;
import com.gang.cloud.template.to.CommonUserTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Created by zengzg
 */
@Component
public interface UserClient {

    List<CommonUserTO> list();

    CommonUserTO getOne(String id);
}
