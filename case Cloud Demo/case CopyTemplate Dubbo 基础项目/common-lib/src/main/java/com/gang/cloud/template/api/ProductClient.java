package com.gang.cloud.template.api;

import com.gang.cloud.template.to.CommonProductTO;

import java.util.List;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Created by zengzg
 */
public interface ProductClient {

    List<CommonProductTO> list();

    CommonProductTO getOne(Integer id);

    String buyProduct(Integer orderId);

}
