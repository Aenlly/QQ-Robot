package top.aenlly.qqrobot.filter;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.message.data.SingleMessage;
import top.aenlly.qqrobot.enmus.OrderedEnum;
import top.aenlly.qqrobot.strategy.ContextStrategy;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;


public class OrdinaryFilterChain extends AbstractFilterChain{

    private ContextStrategy contextStrategy;

    public OrdinaryFilterChain(ContextStrategy contextStrategy) {
        this.contextStrategy = contextStrategy;
    }

    @Override
    public int getOrder() {
        return OrderedEnum.ORDINARY.getOrder();
    }

    @Override
    public void filter(MessageEvent event) {
        // 获取消息
        MessageSource messageSource = MessageUtils.getMessageSource(event);
        if (messageSource == null) {
            filterChain.filter(event);
            return;
        }
        List<SingleMessage> plainText = MessageUtils.getPlainText(event);
        if(plainText.isEmpty()){ return; }
        MessageSourceKind messageSourceKind = messageSource.getKind();
        contextStrategy.processMessage(messageSourceKind,event);
    }
}
