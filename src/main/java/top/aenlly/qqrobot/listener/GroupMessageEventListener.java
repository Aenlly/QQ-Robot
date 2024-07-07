package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.core.common.CommandModeContext;
import top.aenlly.qqrobot.core.common.GeneralContext;

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
        // 判断是否进入了命令模式
        CommandModeContext commandModeContext = GeneralContext.commandStatusMap.get("group:"+event.getBot().getId());
        if(commandModeContext!=null){
            generalContext.getCommandMap().get(commandModeContext.getCommandEnum().name()).execute(event);
        }
        generalContext.getFilterChain().filter(event);
    }
}
