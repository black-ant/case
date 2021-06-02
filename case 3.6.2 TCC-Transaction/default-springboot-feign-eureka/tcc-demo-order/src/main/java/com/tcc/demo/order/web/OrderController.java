package com.tcc.demo.order.web;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tcc.demo.order.model.Order;
import com.tcc.demo.order.model.Product;
import com.tcc.demo.order.repository.ProductRepository;
import com.tcc.demo.order.service.AccountServiceImpl;
import com.tcc.demo.order.service.OrderServiceImpl;
import com.tcc.demo.order.service.PlaceOrderServiceImpl;
import com.tcc.demo.order.web.vo.PlaceOrderRequest;

/**
 * Created by changming.xie on 4/1/16.
 */
@Controller
@RequestMapping("")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlaceOrderServiceImpl placeOrderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    OrderServiceImpl orderService;


    /**
     * 首页界面
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/index");
        return mv;
    }

    @RequestMapping(value = "/user/{userId}/shop/{shopId}", method = RequestMethod.GET)
    public ModelAndView getProductsInShop(@PathVariable long userId,
                                          @PathVariable long shopId) {
        List<Product> products = productRepository.findByShopId(shopId);

        ModelAndView mv = new ModelAndView("/shop");

        mv.addObject("products", products);
        mv.addObject("userId", userId);
        mv.addObject("shopId", shopId);

        return mv;
    }

    /**
     * 购买页面流程
     *
     * @param userId
     * @param shopId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/user/{userId}/shop/{shopId}/product/{productId}/confirm", method = RequestMethod.GET)
    public ModelAndView productDetail(@PathVariable long userId,
                                      @PathVariable long shopId,
                                      @PathVariable long productId) {

        logger.info("------> [开始购买流程 , 返回属性参数] <-------");

        ModelAndView mv = new ModelAndView("product_detail");

        mv.addObject("capitalAmount", accountService.getCapitalAccountByUserId(userId));
        mv.addObject("redPacketAmount", accountService.getRedPacketAccountByUserId(userId));

        mv.addObject("product", productRepository.findById(productId));

        mv.addObject("userId", userId);
        mv.addObject("shopId", shopId);

        return mv;
    }

    /**
     * 支付主流程
     *
     * @param redPacketPayAmount
     * @param shopId
     * @param payerUserId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/placeorder", method = RequestMethod.POST)
    public RedirectView placeOrder(@RequestParam(name = "redPacketPayAmount") Long redPacketPayAmount,
                                   @RequestParam long shopId,
                                   @RequestParam long payerUserId,
                                   @RequestParam long productId) {

        logger.info("------> 购买确认流程 : 商店 ID [{}]  , 购买用户 ID [{}] , 产品ID :[{}] , 红包使用 [{}] <-------", shopId, payerUserId, productId, redPacketPayAmount);


        PlaceOrderRequest request = buildRequest(redPacketPayAmount, shopId, payerUserId, productId);

        String merchantOrderNo = placeOrderService.placeOrder(request.getPayerUserId(), request.getShopId(),
                request.getProductQuantities(), request.getRedPacketPayAmount());

        // 等待异步操作完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/payresult/" + merchantOrderNo);
    }

    /**
     * 获取支付结果
     *
     * @param merchantOrderNo
     * @return
     */
    @RequestMapping(value = "/payresult/{merchantOrderNo}", method = RequestMethod.GET)
    public ModelAndView getPayResult(@PathVariable String merchantOrderNo) {

        ModelAndView mv = new ModelAndView("pay_success");

        String payResultTip = null;
        Order foundOrder = orderService.findOrderByMerchantOrderNo(merchantOrderNo);

        if ("CONFIRMED".equals(foundOrder.getStatus())) {
            payResultTip = "支付成功";
        } else if ("PAY_FAILED".equals(foundOrder.getStatus())) {
            payResultTip = "支付失败";
        } else {
            payResultTip = "Unknown";
        }

        mv.addObject("payResult", payResultTip);

        mv.addObject("capitalAmount", accountService.getCapitalAccountByUserId(foundOrder.getPayerUserId()));
        mv.addObject("redPacketAmount", accountService.getRedPacketAccountByUserId(foundOrder.getPayerUserId()));

        return mv;
    }


    private PlaceOrderRequest buildRequest(Long redPacketPayAmount, long shopId, long payerUserId, long productId) {
        BigDecimal redPacketPayAmountInBigDecimal = new BigDecimal(redPacketPayAmount == null ? 0 : redPacketPayAmount);
        if (redPacketPayAmountInBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidParameterException("invalid red packet amount :" + redPacketPayAmount);
        }

        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setPayerUserId(payerUserId);
        request.setShopId(shopId);
        request.setRedPacketPayAmount(redPacketPayAmountInBigDecimal);
        request.getProductQuantities().add(new ImmutablePair<Long, Integer>(productId, 1));
        return request;
    }
}
