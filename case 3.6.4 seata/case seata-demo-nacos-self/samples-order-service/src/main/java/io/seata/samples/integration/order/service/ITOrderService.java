package io.seata.samples.integration.order.service;

import io.seata.samples.integration.common.dto.OrderDTO;
import io.seata.samples.integration.common.response.ObjectResponse;

/**
 * <p>
 * 创建订单
 * </p>
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface ITOrderService {

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
