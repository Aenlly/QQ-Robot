package top.aenlly.qqrobot.service.impl;

import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aenlly.qqrobot.entity.SysBotEntity;
import top.aenlly.qqrobot.mapper.SysBotMapper;
import top.aenlly.qqrobot.service.LoginService;
import top.aenlly.qqrobot.utils.BotUtils;

import java.util.List;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:35
 * @since 1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private List<SimpleListenerHost> listenerHosts;

    @Autowired
    private SysBotMapper sysBotMapper;

    @SneakyThrows
    @Override
    public void login(Long qq) {
        SysBotEntity sysBotDO = sysBotMapper.selectOne(SysBotEntity::getQq, qq);
        Bot bot = BotUtils.defaultBot(sysBotDO);
        EventChannel<BotEvent> eventChannel = bot.getEventChannel();
        listenerHosts.forEach(eventChannel::registerListenerHost);
        // 获取最新版本协议
        bot.login();
    }
}
