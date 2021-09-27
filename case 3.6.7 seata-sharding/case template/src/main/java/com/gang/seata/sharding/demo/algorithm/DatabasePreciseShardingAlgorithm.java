package com.gang.seata.sharding.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("------------------select database name");
        Long curValue = shardingValue.getValue();
        String curBase = "";
        if (curValue > 0 && curValue <= 200) {
            curBase = "saleorder01";
        } else {
            curBase = "saleorder02";
        }
        return curBase;
    }
}
