package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.core.common.MessageTemplate;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

@Slf4j
@Component
public class T extends AbstractCommand<GroupMessageEvent>  {

    @Override
    public String getName() {
        return CommandEnum.T.name();
    }

    @Override
    protected void after() {
        List<Long> ats = MessageUtils.getAt(event);
        List<Long> optAt = ats.stream().filter(f -> f.equals(event.getBot().getId())).toList();
        Group group = event.getGroup();
        if(ArrayUtil.isNotEmpty(optAt)){
            List<PlainText> plainText = MessageUtils.getCommandPlainText(event);
            String value = MessageTemplate.KICK.getValue();
            if(CollUtil.isNotEmpty(plainText)){
                value=plainText.get(0).contentToString();
            }
            String finalValue = value;
            optAt.forEach(f-> group.get(f).kick(finalValue));
            log.info("group {} kick {}",group.getId(),optAt);
        }
    }
}
