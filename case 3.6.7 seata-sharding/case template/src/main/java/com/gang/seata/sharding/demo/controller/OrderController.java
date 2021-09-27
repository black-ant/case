package com.gang.seata.sharding.demo.controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    /*
    @Resource
    private OrderMapper orderMapper;

    //商品详情 参数:商品id
    @Transactional
    @RequestMapping("/orderadd/{goodsId}/{count}")
    @ResponseBody
    public String orderAdd(@PathVariable Long goodsId,
                             @PathVariable int count) {
        Order order = new Order();
        //得到sn
        String orderSn = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        order.setOrderSn(orderSn);
        order.setOrderStatus(0);
        order.setPrice(new BigDecimal(100.00));
        order.setUserId(8);

        int orderId = orderMapper.insertOneOrder(order);

        if (orderId>0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }


    //订单详情，参数:订单id
    @GetMapping("/orderinfo")
    @ResponseBody
    public Order orderInfo(@RequestParam(value="orderid",required = true,defaultValue = "0") Long orderId) {
        Order order = orderMapper.selectOneOrder(orderId);
        return order;
    }
    */
}
