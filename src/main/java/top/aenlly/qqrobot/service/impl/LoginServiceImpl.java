package top.aenlly.qqrobot.service.impl;

import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aenlly.qqrobot.listener.GroupListener;
import top.aenlly.qqrobot.properties.AccountProperties;
import top.aenlly.qqrobot.service.LoginService;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:35
 * @since 1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AccountProperties accountProperties;
    @Autowired
    private GroupListener listener;

    @SneakyThrows
    @Override
    public void login() {
        Bot bot = BotFactory.INSTANCE.newBot(accountProperties.getQq(), accountProperties.getPassword());
        bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
        bot.getEventChannel().registerListenerHost(listener);
        // 获取最新版本协议
        new Thread("bot.login") {
            @Override
            public void run() {
                bot.login();
                while (true) {
                }
            }
        }.start();
    }
}
