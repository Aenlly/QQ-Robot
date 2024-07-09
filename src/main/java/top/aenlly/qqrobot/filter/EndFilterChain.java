package top.aenlly.qqrobot.filter;

import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.enmus.OrderedEnum;

@Component
public class EndFilterChain extends AbstractFilterChain {

    @Override
    public void filter(MessageEvent event) {

    }

    @Override
    public int getOrder() {
        return OrderedEnum.MAX.getOrder();
    }
}
