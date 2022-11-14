package com.voya.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import com.voya.gateway.handler.SentinelFallbackHandler;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 网关限流配置
 *
 * @author voya
 */
@Configuration
public class GatewayConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
        return new SentinelFallbackHandler();
    }

    @PostConstruct
    public void doInit() {
        // 加载网关限流规则
        initGatewayRules();
    }

//    /**
//     * 网关限流规则
//     */
//    private void initGatewayRules() {
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(new GatewayFlowRule("voya-system")
//                .setCount(3) // 限流阈值
//                .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是 1 秒
//        // 加载网关限流规则
//        GatewayRuleManager.loadRules(rules);
//    }

    /**
     * 网关限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("voya-api")
                .setCount(3) // 限流阈值
                .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是 1 秒
        rules.add(new GatewayFlowRule("code-api")
                .setCount(5) // 限流阈值
                .setIntervalSec(60));
        // 加载网关限流规则
        GatewayRuleManager.loadRules(rules);
        // 加载限流分组
        initCustomizedApis();
    }


    /**
     * 限流分组
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        // voya-system 组
        ApiDefinition api1 = new ApiDefinition("voya-api").setPredicateItems(new HashSet<ApiPredicateItem>() {
            private static final long serialVersionUID = 1L;

            {
                // 匹配 /user 以及其子路径的所有请求
                add(new ApiPathPredicateItem().setPattern("/system/user/**")
                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
            }
        });
        definitions.add(api1);
        // voya-gen 组
        ApiDefinition api2 = new ApiDefinition("code-api").setPredicateItems(new HashSet<ApiPredicateItem>() {
            private static final long serialVersionUID = 1L;

            {
                // 只匹配 /job/list
                add(new ApiPathPredicateItem().setPattern("/code/gen/list"));
            }
        });

        definitions.add(api2);
        // 加载限流分组
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}