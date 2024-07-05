package top.aenlly.qqrobot.bot;

import lombok.SneakyThrows;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import top.aenlly.qqrobot.listener.GroupListener;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;


/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:30
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class BotAutoConfiguration {

    @Bean
    public GroupListener groupListener() {
        return new GroupListener();
    }

    @SneakyThrows
    @Bean
    public GlobalEventChannel configure(GroupListener listener) {
        FixProtocolVersion.load(BotConfiguration.MiraiProtocol.ANDROID_PAD, ResourceUtils.getFile("classpath:config/9.0.56.json"));
        GlobalEventChannel instance = GlobalEventChannel.INSTANCE;
        instance.registerListenerHost(listener);
        return instance;
    }
}
