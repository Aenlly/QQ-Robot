package top.aenlly.qqrobot.service.impl;

import cn.hutool.core.lang.Opt;
import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.GroupDO;
import top.aenlly.qqrobot.entity.SysBotDO;
import top.aenlly.qqrobot.listener.BotListener;
import top.aenlly.qqrobot.mapper.GroupMapper;
import top.aenlly.qqrobot.mapper.SysBotMapper;
import top.aenlly.qqrobot.properties.QQProperties;
import top.aenlly.qqrobot.service.LoginService;
import top.aenlly.qqrobot.strategy.ContextStrategy;
import top.aenlly.qqrobot.utils.BotUtils;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:35
 * @since 1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private BotListener botListener;

    @Autowired
    private SysBotMapper sysBotMapper;

    @SneakyThrows
    @Override
    public void login(Long qq) {
        SysBotDO sysBotDO = sysBotMapper.selectOne(SysBotDO::getQq, qq);
        Bot bot = BotUtils.defaultBot(sysBotDO);
        bot.getEventChannel().registerListenerHost(botListener);
        // 获取最新版本协议
        bot.login();
    }
}
