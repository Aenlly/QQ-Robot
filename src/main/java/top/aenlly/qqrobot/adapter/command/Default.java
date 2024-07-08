package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.util.ArrayUtil;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.entity.BotCommandEntity;
import top.aenlly.qqrobot.mapper.BoCommandMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

@Component
public class Default extends AbstractCommand {
    @Autowired
    private BoCommandMapper boCommandMapper;

    @Lazy
    @Autowired
    private GeneralContext generalContext;


    @Override
    public String getName() {
        return CommandEnum.DEFAULT.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        super.execute(event);
        // 构建查询
        long groupId = event.getGroup().getId();
        BotCommandEntity query = BotCommandEntity.builder()
                .matchValue(context.getCommand().trim())
                .groupId(groupId)
                .at(context.isAt())
                .build();
        List<BotCommandEntity> exactList = boCommandMapper.selectList(query);

        // 判断是否存在自定义命令
        if(ArrayUtil.isNotEmpty(exactList)){
            BotCommandEntity botGroupDO = exactList.get(0);
            if(OptTypeEnum.ORDINARY.equals(botGroupDO.getOptType())){
                MessageUtils.senderQuoteReplyMessage(event,botGroupDO.getRevert());
                return;
            }
            context.setCommand(botGroupDO.getCommand());
            context.setOptType(OptTypeEnum.COMMAND);
            context.setOpenContinuedCommand(null);
            generalContext.getCommandMap().get(botGroupDO.getCommand()).execute(context);
        }
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        super.execute(event);
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        super.execute(event);
    }

    @Override
    protected void execute(StrangerMessageEvent event) {
        super.execute(event);
    }
}
