package top.aenlly.qqrobot.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.GlobalEventChannel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aenlly.qqrobot.listener.GroupListener;
import top.aenlly.qqrobot.properties.AccountProperties;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:30
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class BotAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Bot.class)
    public Bot bot(AccountProperties accountProperties, GroupListener listener) {
        Bot bot = BotFactory.INSTANCE.newBot(accountProperties.getQq(), accountProperties.getPassword());
        bot.getEventChannel().registerListenerHost(listener);
        return bot;
    }

    @Bean
    public GroupListener groupListener() {
        return new GroupListener();
    }

    @Bean
    public GlobalEventChannel configure(GroupListener listener) {
        GlobalEventChannel instance = GlobalEventChannel.INSTANCE;
        instance.registerListenerHost(listener);
        return instance;
    }
}
