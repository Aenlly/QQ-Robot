package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */

public class GroupListener extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }


    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        event.getSubject().sendMessage("received");
        // 无返回值, 表示一直监听事件.
    }
}
