package com.gang.cloud.template.api;

import com.gang.cloud.template.to.CommonOrderTO;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@Component
public interface OrderClient {

    Collection<CommonOrderTO> list();

    CommonOrderTO getOne(Integer id);

}
