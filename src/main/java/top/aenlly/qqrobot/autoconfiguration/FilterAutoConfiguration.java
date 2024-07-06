package top.aenlly.qqrobot.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.filter.CommandFilterChain;
import top.aenlly.qqrobot.filter.OrdinaryFilterChain;
import top.aenlly.qqrobot.strategy.ContextStrategy;

import java.util.List;

@Configuration(proxyBeanMethods=false)
public class FilterAutoConfiguration {

    @Bean
    public CommandFilterChain commandFilterChain(List<Command> commands){
        return new CommandFilterChain(commands);
    }

    @Bean
    public OrdinaryFilterChain ordinaryFilterChain(ContextStrategy contextStrategy){
        return new OrdinaryFilterChain(contextStrategy);
    }
}
