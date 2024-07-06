package top.aenlly.qqrobot.filter;

import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.core.Ordered;

public interface MessageFilterChain extends Ordered {

    void filter(MessageEvent event);
}
