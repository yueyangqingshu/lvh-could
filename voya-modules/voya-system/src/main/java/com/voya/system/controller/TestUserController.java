package com.voya.system.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.voya.system.service.impl.IUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestUserController {
//
//    @Autowired
//    private IUserServiceImpl iUserService;
//
//    @GetMapping("/user/info")
//    public Object info() throws InterruptedException {
//        System.out.printf("执行了");
//        return "{\"username\":\"admin\",\"password\":\"admin123\"}";
//    }
//
//    @GetMapping("/user/test")
//    public Object test() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName() + "执行了！！---");
//        return "返回用户测试";
//    }
//
//    @GetMapping("/user/rt")
//    @SentinelResource(value = "rt", blockHandler = "rtBlockHandler", fallback = "rtFallback")
//    public Object rt() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(2);
//        return "返回用户慢调用比例数据";
//    }
//
//    public Object rtBlockHandler() {
//        return "rtBlockHandler";
//    }
//
//    public Object rtFallback() {
//        return "rtFallback";
//    }


    @GetMapping("/user/ex")
    @SentinelResource(value = "ex", blockHandler = "exBlockHandler", fallback = "exFallback")
    public Object ex() {
        int a = 1 / 0;
        return "返回用户慢调用比例数据";
    }

    // 服务流量控制处理，参数最后多一个 BlockException，其余与原函数一致。
    public Object exBlockHandler(BlockException ex) {
        System.out.println("selectUserByNameBlockHandler异常信息：" + ex.getMessage());
        return "{\"code\":\"500\",\"msg\": \"" + "服务流量控制处理\"}";
    }

    // 服务熔断降级处理，函数签名与原函数一致或加一个 Throwable 类型的参数
    public Object exFallback(Throwable throwable) {
        System.out.println("selectUserByNameFallback异常信息：" + throwable.getMessage());
        return "{\"code\":\"500\",\"msg\": " + "服务熔断降级处理\"}";
    }

//
//    @GetMapping("/info/{username}")
//    public Object info(@PathVariable("username") String username) {
//        return iUserService.selectUserByName(username);
//    }
//
//    @GetMapping("/info2/{username}")
//    public Object info2(@PathVariable("username") String username) {
//        return iUserService.selectUserByName(username);
//    }
}