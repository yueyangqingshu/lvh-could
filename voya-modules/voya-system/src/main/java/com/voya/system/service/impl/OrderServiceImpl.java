package com.voya.system.service.impl;

import javax.annotation.Resource;

import com.voya.common.core.domain.R;
import com.voya.system.api.RemoteFileService;
import com.voya.system.api.domain.SysFileInfo;
import com.voya.system.domain.Order;
import com.voya.system.dto.PlaceOrderRequest;
import com.voya.system.mapper.OrderMapper;
import com.voya.system.service.AccountService;
import com.voya.system.service.OrderService;
import com.voya.system.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.dynamic.datasource.annotation.DS;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class OrderServiceImpl implements OrderService
{
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RemoteFileService remoteFileService;

    @DS("order") // 每一层都需要使用多数据源注解切换所选择的数据库
    @Override
    @Transactional
    @GlobalTransactional // 重点 第一个开启事务的需要添加seata全局事务注解
    public void placeOrder(PlaceOrderRequest request)
    {
        log.info("=============ORDER START=================");
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        Integer amount = request.getAmount();
        log.info("收到下单请求,用户:{}, 商品:{},数量:{}", userId, productId, amount);

        log.info("当前 XID: {}", RootContext.getXID());

        Order order = new Order(userId, productId, 0, amount);

        orderMapper.insert(order);
        log.info("订单一阶段生成，等待扣库存付款中");
        // 测试fegin调用
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileName("name" + order.getId());
        sysFileInfo.setFilePath("/home/ruoyi/name" + order.getId() + ".png");
        R<Boolean> booleanR = remoteFileService.saveFile(sysFileInfo);
        if(booleanR.getCode() != 200){
            throw new RuntimeException("调用远程服务失败");
        }
        log.info("调用文件服务结果：{}", booleanR.getData());

        // 扣减库存并计算总价
        Double totalPrice = productService.reduceStock(productId, amount);
//        int a=10/0;
        // 扣减余额
        accountService.reduceBalance(userId, totalPrice);

        order.setStatus(1);
        order.setTotalPrice(totalPrice);
        orderMapper.updateById(order);
        log.info("订单已成功下单");
        log.info("=============ORDER END=================");
    }

}