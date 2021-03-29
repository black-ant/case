package com.gang.cloud.template.api;

import com.gang.cloud.template.to.CommonAccountTO;
import com.gang.cloud.template.to.CommonUserTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@Component
public interface AccountClient {

    List<CommonAccountTO> list();

    CommonAccountTO getOne(String id);

    String buyProduct(Integer orderId);
}
