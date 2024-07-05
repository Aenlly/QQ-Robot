package top.aenlly.qqrobot.service.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactOrBot;
import org.springframework.stereotype.Service;
import top.aenlly.qqrobot.service.BotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aenlly||tnw
 * @create 2024/07/05 14:52
 * @since 1.0.0
 */
@Service
public class BotServiceImpl implements BotService {
    @Override
    public List<Long> getBotList() {
        List<Bot> instances = Bot.getInstances();
        return instances.stream().map(ContactOrBot::getId).collect(Collectors.toList());
    }
}
