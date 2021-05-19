package io.seata.samples.integration.storage.mapper;

import io.seata.samples.integration.storage.entity.TStorage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Mapper 接口
 * </p>
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */

public interface TStorageDao extends JpaRepository<TStorage, Integer> {

}
