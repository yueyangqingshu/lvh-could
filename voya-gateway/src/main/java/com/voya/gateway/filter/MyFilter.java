//package com.voya.gateway.filter;
//
//import com.voya.common.core.utils.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Date;
//
//@Component
//@Slf4j
//public class MyFilter implements Ordered, GlobalFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String id = exchange.getRequest().getQueryParams().getFirst("id");
//        //打印当前时间
//        log.info("MyFilter 当前请求时间为:" + new Date());
//        if (StringUtils.isEmpty(id)) {
//            log.info("用户名不存在，非法请求！");
//            //如果username为空，返回状态码为407，需要代理身份验证
//            exchange.getResponse().setStatusCode(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
//            //后置过滤器
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        System.out.printf("getOrder");
//        return 0;
//    }
//}
