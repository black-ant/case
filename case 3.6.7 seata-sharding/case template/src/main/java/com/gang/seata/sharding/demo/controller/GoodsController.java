package com.gang.seata.sharding.demo.controller;

import com.gang.seata.sharding.demo.mapper.GoodsMapper;
import com.gang.seata.sharding.demo.pojo.Goods;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Resource
    private GoodsMapper goodsMapper;

    //更新商品库存 参数:商品id
    @RequestMapping("/goodsstock/{goodsId}/{count}")
    @ResponseBody
    public String goodsStock(@PathVariable Long goodsId,
                             @PathVariable int count) {

        TransactionTypeHolder.set(TransactionType.BASE);
        int res = goodsMapper.updateGoodsStock(goodsId, count);
        System.out.println("res:" + res);

        if (res > 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

    //商品详情 参数:商品id
    @GetMapping("/goodsinfo")
    @ResponseBody
    public Goods goodsInfo(@RequestParam(value = "goodsid", required = true, defaultValue = "0") Long goodsId) {
        Goods goods = goodsMapper.selectOneGoods(goodsId);
        return goods;
    }

}
