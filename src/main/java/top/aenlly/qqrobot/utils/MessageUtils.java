package top.aenlly.qqrobot.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import top.aenlly.qqrobot.constant.CommonConstant;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    public static MessageSource getMessageSource(MessageEvent event){
        return event.getMessage().get(MessageSource.Key);
    }

    public static List<Long> getAt(MessageEvent event){
        return event.getMessage().stream().filter(At.class::isInstance).map(f->((At)f).getTarget()).collect(Collectors.toList());
    }

    public static MessageContent getMessageContent(MessageEvent event){
        return event.getMessage().get(MessageContent.Key);
    }

    public static List<SingleMessage> getPlainText(MessageEvent event){
        return event.getMessage().stream().filter(PlainText.class::isInstance).filter(f-> !StrUtil.isBlank(f.contentToString())).collect(Collectors.toList());
    }

    public static String getMessageStr(MessageEvent event){
        StringBuilder stringBuffer=new StringBuilder();
        event.getMessage()
                .stream()
                .filter(PlainText.class::isInstance)
                .map(Message::contentToString)
                .filter(StrUtil::isNotEmpty)
                .peek(stringBuffer::append)
                .count();
        return stringBuffer.toString();
    }

    /**
     * 获取命令后剩余内容
     * @param event
     * @return
     */
    public static Pair<String,String> getCommandPlainText(MessageEvent event){
        List<PlainText> list = event.getMessage().stream().filter(PlainText.class::isInstance).filter(f -> !StrUtil.isBlank(f.contentToString())).map(m->(PlainText)m).collect(Collectors.toList());
        if(CollUtil.isEmpty(list)){
            return new Pair<>(null,null);
        }

        StringBuffer sbf=new StringBuffer();

        for (PlainText plainText : list) {
            sbf.append(plainText.contentToString().trim());
        }

        String[] strs=sbf.toString().split(CommonConstant.HSIANG_HSIEN,2);
        return new Pair<>(strs[0].trim(), strs.length>1?strs[1].trim():null);
    }


    public static QuoteReply getQuoteReply(MessageEvent event){
        return event.getMessage().get(QuoteReply.Key);
    }

    public static void senderQuoteReplyMessage(MessageEvent event,String revert){
        event.getSubject().sendMessage(new MessageChainBuilder() // 引用收到的消息并回复, 也可以添加图片等更多元素.
                .append(new QuoteReply(event.getMessage()))
                .append(revert)
                .build());
    }
}
