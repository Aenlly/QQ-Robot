package top.aenlly.qqrobot.listener;

import cn.hutool.core.lang.Pair;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.context.CommandContext;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.core.common.CommandModeContext;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.utils.MessageUtils;

/**
 * 消息处理事件
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@Component
public class FriendMessageEventListener extends SimpleListenerHost {


    private GeneralContext generalContext;

    public FriendMessageEventListener(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull FriendMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        // 判断是否进入了命令模式
        Pair<String, String> stringPair = MessageUtils.getCommandPlainText(event);

        CommandModeContext commandModeContext = GeneralContext.COMMAND_CACHE_MAP.get(MessageSourceKind.FRIEND.name() + event.getBot().getId());
        CommandContext.CommandContextBuilder builder = CommandContext.builder()
                .command(commandModeContext.getCommandEnum().name())
                .optType(OptTypeEnum.COMMAND)
                .content(stringPair.getValue())
                .command(stringPair.getKey())
                .event(event);
        if(commandModeContext != null){
            String content = MessageUtils.getMessageStr(event);
            builder.openContinuedCommand(true);
            CommandContext commandContext = builder.content(content).build();
            generalContext.getCommandMap().get(commandModeContext.getCommandEnum().name()).execute(commandContext);
            return;
        }
        // 先判断命令是否是内置命令，不存在则走默认(数据库查询)
        generalContext.getCommandMap()
                .getOrDefault(stringPair.getKey(), generalContext.getCommandMap().get(CommandEnum.DEFAULT.name()))
                .execute(builder.build());

    }
}
