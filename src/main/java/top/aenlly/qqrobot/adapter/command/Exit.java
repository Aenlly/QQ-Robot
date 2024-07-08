package top.aenlly.qqrobot.adapter.command;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.enmus.CommandEnum;

@Component
public class Exit extends AbstractCommand{
    @Override
    public String getName() {
        return CommandEnum.EXIT.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        GeneralContext.COMMAND_CACHE_MAP.remove("group:"+ event.getGroup().getId());
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        GeneralContext.COMMAND_CACHE_MAP.remove("friend:"+ event.getFriend().getId());
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        GeneralContext.COMMAND_CACHE_MAP.remove("temp:"+ event.getSender().getGroup().getBot().getId());
    }

    @Override
    protected void execute(StrangerMessageEvent event) {
        GeneralContext.COMMAND_CACHE_MAP.remove("stranger:"+ event.getStranger().getBot().getId());
    }
}
