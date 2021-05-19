package io.seata.samples.integration.storage.service;

import io.seata.samples.integration.common.dto.CommodityDTO;
import io.seata.samples.integration.common.response.ObjectResponse;

/**
 * 仓库服务
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface ITStorageService {

    /**
     * 扣减库存
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
