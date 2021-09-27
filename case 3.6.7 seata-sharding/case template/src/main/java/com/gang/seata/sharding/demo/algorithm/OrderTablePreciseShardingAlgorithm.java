package com.gang.seata.sharding.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        //System.out.println("--------==================doSharding");

        Long curValue = shardingValue.getValue();
        String curTable = "";
        if (curValue > 0 && curValue <= 100) {
            curTable = "t_order_1";
        } else if (curValue > 100 && curValue <= 200) {
            curTable = "t_order_2";
        } else if (curValue > 200 && curValue <= 300) {
            curTable = "t_order_3";
        } else {
            curTable = "t_order_4";
        }
        return curTable;
    }

}
