package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderJob
 * @Descrintion
 * @Author bd
 * @Date 2020/6/6 23:27
 * @Version 1.0
 **/
@Component
public class OrderJob {
    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();



    }
}
