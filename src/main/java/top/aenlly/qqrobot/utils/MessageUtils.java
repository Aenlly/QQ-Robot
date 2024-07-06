package top.aenlly.qqrobot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    public static MessageSource getMessageSource(MessageEvent event){
        return event.getMessage().get(MessageSource.Key);
    }

    public static List<Long> getAt(MessageEvent event){
        return event.getMessage().stream().filter(f->f instanceof At).map(f->((At)f).getTarget()).collect(Collectors.toList());
    }

    public static MessageContent getMessageContent(MessageEvent event){
        return event.getMessage().get(MessageContent.Key);
    }

    public static List<SingleMessage> getPlainText(MessageEvent event){
        return event.getMessage().stream().filter(f->f instanceof PlainText).collect(Collectors.toList());
    }


    public static QuoteReply getQuoteReply(MessageEvent event){
        return event.getMessage().get(QuoteReply.Key);
    }

    public static MessageChain  buildQuoteReplyMessage(MessageEvent event,String revert){
        return new MessageChainBuilder() // 引用收到的消息并回复, 也可以添加图片等更多元素.
                .append(new QuoteReply(event.getMessage()))
                .append(revert)
                .build();
    }
}
