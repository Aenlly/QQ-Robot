package top.aenlly.qqrobot.adapter.command;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.SingleMessage;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

public class Help implements Command {
    @Override
    public String getName() {
        return CommandEnum.HELP.name();
    }

    @Override
    public void execute(MessageEvent event) {
        List<SingleMessage> plainText = MessageUtils.getPlainText(event);
        plainText.remove(0);
    }
}
