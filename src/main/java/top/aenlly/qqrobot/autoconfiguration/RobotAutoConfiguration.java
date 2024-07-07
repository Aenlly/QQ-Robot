package top.aenlly.qqrobot.autoconfiguration;

import lombok.SneakyThrows;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:30
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class RobotAutoConfiguration {

    @SneakyThrows
    @Bean
    public GlobalEventChannel configure() {

        GlobalEventChannel instance = GlobalEventChannel.INSTANCE;
        instance.subscribeAlways(GroupEvent.class, event->{

        });
//        instance.registerListenerHost(listener);
        return instance;
    }
}
