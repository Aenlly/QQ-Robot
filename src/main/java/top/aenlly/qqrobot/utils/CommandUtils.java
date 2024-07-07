package top.aenlly.qqrobot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.enmus.CommandEnum;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandUtils {

    /**
     * 执行命令
     * @param event
     * @param commandHolder
     */
    public static void commandExecute(MessageEvent event, Map<String, Command> commandHolder) {
        List<PlainText> plainText = MessageUtils.getCommandPlainText(event);
        String command = CommandEnum.DEFAULT.name();
        if(!plainText.isEmpty()) {
            command = plainText.get(0).contentToString();
        }
        commandHolder.get(command).execute(event);
    }

}
