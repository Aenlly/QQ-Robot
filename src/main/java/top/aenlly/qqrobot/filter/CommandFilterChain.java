package top.aenlly.qqrobot.filter;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.SingleMessage;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.OrderedEnum;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandFilterChain extends AbstractFilterChain {

    private Map<String, Command> commandMap;

    public CommandFilterChain(List<Command> commands) {
        commandMap = commands.stream().collect(Collectors.toMap(Command::getName,c->c));
    }

    @Override
    public int getOrder() {
        return OrderedEnum.COMMAND.getOrder();
    }

    @Override
    public void filter(MessageEvent event) {
        List<Long> ats = MessageUtils.getAt(event);
        List<SingleMessage> plainText = MessageUtils.getPlainText(event);
        if(ats.size() != 1){
            filterChain.filter(event);
        }
        String command = CommandEnum.DEFAULT.name();
        if(!plainText.isEmpty()) {
            command = plainText.get(0).contentToString();
        }
        commandMap.get(command).execute(event);

    }
}
