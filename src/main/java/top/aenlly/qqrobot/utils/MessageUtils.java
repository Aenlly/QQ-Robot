package top.aenlly.qqrobot.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import top.aenlly.qqrobot.constant.CommonConstant;

import java.util.ArrayList;
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
        return event.getMessage().stream().filter(f->f instanceof PlainText).filter(f-> !StrUtil.isBlank(f.contentToString())).collect(Collectors.toList());
    }

    /**
     * 获取命令后剩余内容
     * @param event
     * @return
     */
    public static List<PlainText> getCommandPlainText(MessageEvent event){
        List<PlainText> list = event.getMessage().stream().filter(f -> f instanceof PlainText).filter(f -> !StrUtil.isBlank(f.contentToString())).map(m->(PlainText)m).collect(Collectors.toList());
        if(CollUtil.isEmpty(list)){
            return list;
        }

        String msg = list.get(0).contentToString().trim();
        String[] strs=msg.split(CommonConstant.HSIANG_HSIEN,2);
        list.remove(0);

        List<PlainText> result=new ArrayList<>();
        PlainText plainText = new PlainText(strs[0].trim());
        result.add(plainText);
        if(strs.length>1) {
            PlainText plainText1 = new PlainText(strs[1].trim());
            result.add(plainText1);
        }
        result.addAll(list);
        return result;
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
