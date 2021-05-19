package io.seata.samples.integration.account.mapper;

import io.seata.samples.integration.account.entity.TAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface TAccountDao extends JpaRepository<TAccount, Integer> {

    /**
     * 减少账户余额
     *
     * @param userId
     * @param amount
     * @return
     */
    @Query(" update TAccount t set t.amount = t.amount-:amount where t.userId = :userId")
    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);
}
