package com.voya.modules.monitor.config;

/**
 * @ClassName RuoYiStatusChangeNotifier
 * @Description TODO
 * @Author lvh
 * @Date 2022/10/20 20:58
 * @Version 1.0
 */


import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 通知发送配置
 *
 * @author ruoyi
 */
@Component
public class RuoYiStatusChangeNotifier extends AbstractStatusChangeNotifier {
    public RuoYiStatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        return super.shouldNotify(event, instance);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        String serverName = instance.getRegistration().getName();
        InstanceId instanceId = event.getInstance();
        String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        System.out.print("Instance {} ({}) is {}"+ serverName+instanceId+status);
        return Mono.fromRunnable(() -> {
            switch (status) {
                // 健康检查没通过
                case "DOWN":
                    System.out.println("发送 健康检查没通过 的通知！");
                    break;
                // 服务离线
                case "OFFLINE":
                    System.out.println("发送 服务离线 的通知！");
                    break;
                // 服务上线
                case "UP":
                    System.out.println("发送 服务上线 的通知！");
                    break;
                // 服务未知异常
                case "UNKNOWN":
                    System.out.println("发送 服务未知异常 的通知！");
                    break;
                default:
                    break;
            }
        });
    }
}