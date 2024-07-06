package top.aenlly.qqrobot.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aenlly.qqrobot.mapper.GroupMapper;
import top.aenlly.qqrobot.strategy.*;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class StrategyAutoConfiguration {

    @Bean
    public MessageStrategy friendStrategy(){
        return new FriendStrategy();
    }

    @Bean
    public MessageStrategy groupStrategy(GroupMapper groupMapper){
        return new GroupStrategy(groupMapper);
    }

    @Bean
    public MessageStrategy tempStrategy(){
        return new TempStrategy();
    }

    @Bean
    public MessageStrategy strangerStrategy(){
        return new StrangerStrategy();
    }

    @Bean
    public ContextStrategy contextStrategy(List<MessageStrategy> messageStrategies){
        return new ContextStrategy(messageStrategies);
    }
}
