package io.seata.samples.integration.order.mapper;

import io.seata.samples.integration.order.entity.TOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * * @author lidong
 * @since 2019-09-04
 */
public interface TOrderDao extends JpaRepository<TOrder, Integer> {

}
