package top.aenlly.qqrobot.filter;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.enmus.OrderedEnum;
import top.aenlly.qqrobot.mapper.GroupMapper;
import top.aenlly.qqrobot.utils.CommandUtils;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandFilterChain extends AbstractFilterChain {

    private final Map<String, Command> commandHolder;

    private GroupMapper groupMapper;

    public CommandFilterChain(List<Command> commands) {
        commandHolder = commands.stream().collect(Collectors.toMap(Command::getName,c->c));
    }

    @Override
    public int getOrder() {
        return OrderedEnum.COMMAND.getOrder();
    }

    @Override
    public void filter(MessageEvent event) {
        if(event instanceof FriendMessageEvent){
            CommandUtils.commandExecute(event,commandHolder);
            return;
        }
        List<Long> ats = MessageUtils.getAt(event);
        if(ats.size() != 1){
            filterChain.filter(event);
            return;
        }
        CommandUtils.commandExecute(event,commandHolder);
    }
}
