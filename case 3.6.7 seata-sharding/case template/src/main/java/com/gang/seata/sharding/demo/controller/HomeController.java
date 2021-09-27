package com.gang.seata.sharding.demo.controller;

import com.gang.seata.sharding.demo.mapper.GoodsMapper;
import com.gang.seata.sharding.demo.mapper.OrderShardingMapper;
import com.gang.seata.sharding.demo.pojo.OrderSharding;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Resource
    private OrderShardingMapper orderShardingMapper;

    @Resource
    private GoodsMapper goodsMapper;

    //订单列表，列出分库分表的数据
    @GetMapping("/orderlist")
    public String list(Model model, @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {

        PageHelper.startPage(currentPage, 5);
        List<OrderSharding> orderList = orderShardingMapper.selectAllOrder();
        model.addAttribute("orderlist", orderList);
        PageInfo<OrderSharding> pageInfo = new PageInfo<>(orderList);
        model.addAttribute("pageInfo", pageInfo);
        System.out.println("------------------------size:" + orderList.size());
        return "order/list";
    }

    //添加一个订单,访问一个数据库和分库分表的两个数据库
    @GetMapping("/addorder")
    @ResponseBody
    @GlobalTransactional(timeoutMills = 300000, rollbackFor = Exception.class)
    public String addOrder(@RequestParam(value = "orderid", required = true, defaultValue = "0") Long orderId,
                           @RequestParam(value = "isfail", required = true, defaultValue = "0") int isFail
    ) throws SQLException, IOException {

        String goodsId = "3";
        String goodsNum = "1";

        String goodsName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        OrderSharding orderOne = new OrderSharding();
        orderOne.setOrderId(orderId);
        orderOne.setGoodsName(goodsName);

        TransactionTypeHolder.set(TransactionType.BASE);
        int resIns = orderShardingMapper.insertOneOrder(orderOne);
        System.out.println("orderId:" + orderOne.getOrderId());


        TransactionTypeHolder.set(TransactionType.BASE);
        int count = -1;
        int res = goodsMapper.updateGoodsStock(Long.parseLong(goodsId), count);
        System.out.println("res:" + res);

        if (isFail == 1) {
            int divide = 0;
            int resul = 100 / divide;
        }
        if (res > 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }


    //添加一个订单,访问一个数据库和分库分表的两个数据库,rest方式
    @GetMapping("/addorderrest")
    @ResponseBody
    @GlobalTransactional(timeoutMills = 300000, rollbackFor = Exception.class)
    public String addOrderrest(@RequestParam(value = "orderid", required = true, defaultValue = "0") Long orderId,
                               @RequestParam(value = "isfail", required = true, defaultValue = "0") int isFail
    ) throws SQLException, IOException {

        String goodsId = "3";
        String goodsNum = "1";

        String goodsName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        OrderSharding orderOne = new OrderSharding();
        orderOne.setOrderId(orderId);
        orderOne.setGoodsName(goodsName);

        TransactionTypeHolder.set(TransactionType.BASE);
        int resIns = orderShardingMapper.insertOneOrder(orderOne);
        System.out.println("orderId:" + orderOne.getOrderId());


        /*
        TransactionTypeHolder.set(TransactionType.BASE);
        int count = -1;
        int res = goodsMapper.updateGoodsStock(Long.parseLong(goodsId),count);
        System.out.println("res:"+res);
        */

        RestTemplate restTemplate = new RestTemplate();

        String xid = RootContext.getXID();
        System.out.println("xid before send:" + xid);

        HttpHeaders headers = new HttpHeaders();
        headers.add(RootContext.KEY_XID, xid);

        //System.out.println("xid not null");
        //String urlAddOrder = "http://127.0.0.1:8080/order/orderadd/"+goodsId+"/"+goodsNum+"/";

        String goodsUPNum = "-1";
        String urlUpStock = "http://127.0.0.1:8080/goods/goodsstock/" + goodsId + "/" + goodsUPNum + "/";
        String resultUp = restTemplate.postForObject(urlUpStock, new HttpEntity<String>(headers), String.class);
        if (!SUCCESS.equals(resultUp)) {
            throw new RuntimeException();
        }
        if (isFail == 1) {
            int divide = 0;
            int resul = 100 / divide;
        }
        return SUCCESS;
    }

}

