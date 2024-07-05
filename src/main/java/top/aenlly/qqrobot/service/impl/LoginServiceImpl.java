package top.aenlly.qqrobot.service.impl;

import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aenlly.qqrobot.service.LoginService;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:35
 * @since 1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Bot bot;

    ScheduledThreadPoolExecutor taskExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void login() {
        bot.login();
    }
}
