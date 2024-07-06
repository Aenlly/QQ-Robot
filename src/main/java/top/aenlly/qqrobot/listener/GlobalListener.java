package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.message.data.SingleMessage;
import org.jetbrains.annotations.NotNull;
import top.aenlly.qqrobot.strategy.ContextStrategy;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
public class GlobalListener extends SimpleListenerHost {


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        // 获取消息
        MessageSource messageSource = MessageUtils.getMessageSource(event);
        List<Long> at = MessageUtils.getAt(event);
        List<SingleMessage> plainText = MessageUtils.getPlainText(event);
        if(at.size()>1 || messageSource == null || plainText.size()>1){
            throw new RuntimeException("不符合规则");
        }
    }
}
