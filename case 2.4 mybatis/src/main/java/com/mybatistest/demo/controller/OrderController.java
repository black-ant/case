package com.mybatistest.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mybatistest.demo.entity.MyOrder;
import com.mybatistest.demo.service.OrderService;
import com.mybatistest.demo.util.common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@RestController
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    /**
     * 根据Name 查询
     *
     * @return String JSON 格式数据
     */
    @GetMapping("findByOrderName")
    public String findByOrderName(@RequestParam("name") String name) {
        return JSONObject.toJSONString(orderService.findByOrderName(name));
    }

    /**
     * 查询所有
     *
     * @return String JSON 格式数据
     */
    @GetMapping("findall")
    public String findAll() {
        return JSONObject.toJSONString(orderService.findAll());
    }

    /**
     * 查询所有
     *
     * @return String JSON 格式数据
     */
    @GetMapping("findBySome")
    public String findBySome(@RequestParam("type") String type, @RequestBody Map<String, Object> upMap) {
        switch (type) {
            case "1":
                return JSONObject.toJSONString(orderService.findTwoTable());
            case "2":
                return JSONObject.toJSONString(orderService.findTwoTableMany());
            case "3":
                try {
                    return JSONObject.toJSONString(orderService.findInclude(common.stringToDate((String) upMap.get("start"))));
                } catch (ParseException e) {
                    logger.error("时间转换错误");
                }
            case "4":
                return JSONObject.toJSONString(orderService.findNoParam((String) upMap.get("key"), (String) upMap.get("value")));
            default:
                return "is failure";
        }

    }

    /**
     * 此处是将数据JSON 数据放入 RequestBody 中 ，注意 传输格式为 application/json
     * {"ordername":"test002",	"orderdesc":"test002 desc",	"active":1,"status":1,"price":150}
     *
     * @param order
     * @return
     */
    @GetMapping("addOne")
    public String addOne(@RequestBody MyOrder order) {
        logger.info(JSON.toJSONString(order));
        order.setStartdate(new Date());
        return JSONObject.toJSONString(orderService.insertOne(order));
    }

    /**
     * 此处是form 表单格式 Content-Disposition: form-data;
     * form 表单直接用 实体接即可 ，HttpMessageConverter 会自动转换
     *
     * @param order
     * @return
     */
    @GetMapping("addonenobody")
    public String addOneNoBody(MyOrder order) {
        logger.info(JSON.toJSONString(order));
        order.setStartdate(new Date());
        return JSONObject.toJSONString(orderService.insertOne(order));
    }

    /**
     * 此处是form 表单格式 Content-Disposition: form-data;
     * form 表单直接用 实体接即可 ，HttpMessageConverter 会自动转换
     *
     * @param order
     * @return
     */
    @GetMapping("addorderhastype")
    public String addOrderByType(MyOrder order, @RequestParam("type") String type) {
        logger.info(JSON.toJSONString(order));
        order.setStartdate(new Date());
        switch (type) {
            case "1":
                return JSONObject.toJSONString(orderService.insertOneReturnId(order));
            case "2":
                List<MyOrder> list = new LinkedList<>();
                list.add(new MyOrder("manyone", "manyonedesc", BigDecimal.valueOf(100.0)));
                list.add(new MyOrder("manytwo", "manytwodesc", BigDecimal.valueOf(120.0)));
                return JSONObject.toJSONString(orderService.insertMany(list));
            case "3":
                return JSONObject.toJSONString(orderService.insertSearch(order));
            default:
                return "is failure";
        }
    }

    /**
     * 更新数据
     *
     * @param type String
     * @return
     */
    @GetMapping("updateone")
    public String updateOne(@RequestParam("type") String type, @RequestBody Map<String, Object> upMap) {
        logger.info("map msg :{}", JSON.toJSONString(upMap));
        switch (type) {
            case "1":
                return JSONObject.toJSONString(orderService.updateDescByid((Integer) upMap.get("id"), (String) upMap.get("orderdesc")));
            case "2":
                return JSONObject.toJSONString(orderService.updateNumDescByid((Integer) upMap.get("id"), (String) upMap.get("orderdesc")));
            case "3":
                MyOrder order = JSON.parseObject(JSON.toJSONString(upMap), MyOrder.class);
                return JSONObject.toJSONString(orderService.updateMany(order));

            default:
                return "is failure";
        }
    }

    /**
     * 更新数据
     *
     * @param type String
     * @return
     */
    @GetMapping("delete")
    public String deleteOrder(@RequestParam("type") String type, @RequestBody Map<String, Object> upMap) {
        logger.info("map msg :{}", JSON.toJSONString(upMap));
        switch (type) {
            case "1":
                return JSONObject.toJSONString(orderService.deleteByPrimaryKey((Integer) upMap.get("id")));
            case "2":
            default:
                return "is failure";
        }
    }
}
