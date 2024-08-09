package top.aenlly.qqrobot.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.context.CommandContext;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.core.common.CommandModeContext;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

/**
 * 群消息处理事件
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@Component
public class GroupMessageEventListener extends SimpleListenerHost {

    private GeneralContext generalContext;

    public GroupMessageEventListener(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        List<Long> ats = MessageUtils.getAt(event);
        Pair<String, String> stringPair = MessageUtils.getCommandPlainText(event);
        if(CollUtil.isEmpty(ats) || !ats.get(0).equals(event.getBot().getId())){
            generalContext.getCommandMap()
                    .get(CommandEnum.DEFAULT.name())
                    .execute(CommandContext.builder()
                            .optType(OptTypeEnum.ORDINARY)
                            .command(stringPair.getKey())
                            .content(stringPair.getValue())
                            .event(event)
                            .build());
            return;
        }

        CommandContext.CommandContextBuilder builder = CommandContext.builder()
                .optType(OptTypeEnum.COMMAND)
                .command(stringPair.getKey())
                .content(stringPair.getValue())
                .event(event);

        CommandModeContext commandModeContext = GeneralContext.COMMAND_CACHE_MAP.get(MessageSourceKind.GROUP.name() + event.getGroup().getId()+event.getSender().getId());
        // 判断是否进入了命令模式
        if(commandModeContext!=null){
            String content = MessageUtils.getMessageStr(event);
            builder.openContinuedCommand(true);
            builder.command(commandModeContext.getCommandEnum().name());
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
