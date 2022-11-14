package com.voya.system.service.impl;

import com.voya.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * 用户实现
 *
 * @author ruoyi
 */
@Service
public class IUserServiceImpl implements IUserService {
    @Autowired
    private RestTemplate restTemplate;


    @Override
    @SentinelResource(value = "selectUserByName", blockHandler = "selectUserByNameBlockHandler", fallback = "selectUserByNameFallback")
    public Object selectUserByName(String username) {
        return restTemplate.getForObject("http://localhost:9204/user/info/" + username, String.class);
    }

    // 服务流量控制处理，参数最后多一个 BlockException，其余与原函数一致。
    public Object selectUserByNameBlockHandler(String username, BlockException ex) {
        System.out.println("selectUserByNameBlockHandler异常信息：" + ex.getMessage());
        return "{\"code\":\"500\",\"msg\": \"" + username + "服务流量控制处理\"}";
    }

    // 服务熔断降级处理，函数签名与原函数一致或加一个 Throwable 类型的参数
    public Object selectUserByNameFallback(String username, Throwable throwable) {
        System.out.println("selectUserByNameFallback异常信息：" + throwable.getMessage());
        return "{\"code\":\"500\",\"msg\": \"" + username + "服务熔断降级处理\"}";
    }

}