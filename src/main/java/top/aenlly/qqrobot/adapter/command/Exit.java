package top.aenlly.qqrobot.adapter.command;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.core.common.GeneralContext;
import top.aenlly.qqrobot.enmus.CommandEnum;

@Component
public class Exit extends AbstractCommand{
    @Override
    public String getName() {
        return CommandEnum.EXIT.name();
    }

    @Override
    protected void after() {
        if(event instanceof GroupMessageEvent){
            GeneralContext.commandStatusMap.remove("group:"+((GroupMessageEvent) event).getGroup().getId());
        }
        if(event instanceof FriendMessageEvent){
            GeneralContext.commandStatusMap.remove("friend:"+((FriendMessageEvent) event).getFriend().getId());
        }
    }
}
