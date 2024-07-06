package top.aenlly.qqrobot.autoconfiguration;

import lombok.SneakyThrows;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aenlly.qqrobot.filter.AbstractFilterChain;
import top.aenlly.qqrobot.filter.MessageFilterChain;
import top.aenlly.qqrobot.listener.BotListener;
import top.aenlly.qqrobot.strategy.ContextStrategy;

import java.util.Comparator;
import java.util.List;


/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:30
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class RobotAutoConfiguration {

    @Bean
    public BotListener botListener(List<AbstractFilterChain> filterChains){
        filterChains.sort(Comparator.comparing(MessageFilterChain::getOrder));
        for (int i = 0; i < filterChains.size()-1; ) {
            filterChains.get(i).nextFilterChain(filterChains.get(++i));
        }
        return new BotListener(filterChains.get(0));
    }

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
