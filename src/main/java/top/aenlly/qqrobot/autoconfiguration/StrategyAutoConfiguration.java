package top.aenlly.qqrobot.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aenlly.qqrobot.adapter.Command;
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
    public MessageStrategy groupStrategy(GroupMapper groupMapper, List<Command> commands){
        return new GroupStrategy(groupMapper,commands);
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
