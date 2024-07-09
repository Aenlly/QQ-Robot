package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.util.ArrayUtil;
import net.mamoe.mirai.event.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.entity.BotFriendCommandEntity;
import top.aenlly.qqrobot.entity.BotGroupCommandEntity;
import top.aenlly.qqrobot.mapper.BotFriendCommandMapper;
import top.aenlly.qqrobot.mapper.BotGroupCommandMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

@Component
public class Default extends AbstractCommand {
    @Autowired
    private BotGroupCommandMapper botGroupCommandMapper;
    @Autowired
    private BotFriendCommandMapper botFriendCommandMapper;

    @Lazy
    @Autowired
    private GeneralContext generalContext;


    @Override
    public String getName() {
        return CommandEnum.DEFAULT.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        // 构建查询
        long groupId = event.getGroup().getId();
        BotGroupCommandEntity query = BotGroupCommandEntity.builder()
                .matchValue(context.getCommand().trim())
                .groupId(groupId)
                .at(context.isAt())
                .build();
        groupCommand(event, query);
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        // 构建查询
        long friend = event.getFriend().getId();
        BotFriendCommandEntity query = BotFriendCommandEntity.builder()
                .matchValue(context.getCommand().trim())
                .friendId(friend)
                .build();
        List<BotFriendCommandEntity> exactList = botFriendCommandMapper.selectList(query);

        // 判断是否存在自定义命令
        if(ArrayUtil.isNotEmpty(exactList)){
            BotFriendCommandEntity botGroupDO = exactList.get(0);
            context.setCommand(botGroupDO.getCommand());
            context.setOptType(OptTypeEnum.COMMAND);
            context.setOpenContinuedCommand(null);
            generalContext.getCommandMap().get(botGroupDO.getCommand()).execute(context);
        }
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        long groupId = event.getGroup().getId();
        BotGroupCommandEntity query = BotGroupCommandEntity.builder()
                .matchValue(context.getCommand().trim())
                .groupId(groupId)
                .build();
        groupCommand(event, query);
        super.execute(event);
    }


    private void groupCommand(MessageEvent event, BotGroupCommandEntity query) {
        List<BotGroupCommandEntity> exactList = botGroupCommandMapper.selectList(query);

        // 判断是否存在自定义命令
        if(ArrayUtil.isNotEmpty(exactList)){
            BotGroupCommandEntity botGroupDO = exactList.get(0);
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
    protected void execute(StrangerMessageEvent event) {
        super.execute(event);
    }
}
