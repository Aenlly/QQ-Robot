package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@Component
public class GlobalListener extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception {

    }
}
