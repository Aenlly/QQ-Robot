package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceKind;
import org.jetbrains.annotations.NotNull;
import top.aenlly.qqrobot.filter.MessageFilterChain;
import top.aenlly.qqrobot.strategy.ContextStrategy;
import top.aenlly.qqrobot.utils.MessageUtils;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@AllArgsConstructor
public class BotListener extends SimpleListenerHost {

    private MessageFilterChain filterChain;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        filterChain.filter(event);

        // event.getSubject().sendMessage("received");
        // 无返回值, 表示一直监听事件.
    }
}
