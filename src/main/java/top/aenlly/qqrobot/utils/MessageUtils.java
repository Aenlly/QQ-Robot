package top.aenlly.qqrobot.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.aenlly.qqrobot.constant.CommonConstant;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(MessageUtils.class);


    public static MessageSource getMessageSource(MessageEvent event) {
        return event.getMessage().get(MessageSource.Key);
    }

    public static List<Long> getAt(MessageEvent event) {
        return event.getMessage().stream().filter(At.class::isInstance).map(f -> ((At) f).getTarget()).collect(Collectors.toList());
    }

    public static MessageContent getMessageContent(MessageEvent event) {
        return event.getMessage().get(MessageContent.Key);
    }

    public static List<SingleMessage> getPlainText(MessageEvent event) {
        return event.getMessage()
                .stream()
                .filter(PlainText.class::isInstance)
                .filter(f -> !StrUtil.isBlank(f.contentToString()))
                .collect(Collectors.toList());
    }

    public static String getMessageStr(MessageEvent event) {
        StringBuilder stringBuffer = new StringBuilder();
        event.getMessage()
                .stream()
                .filter(PlainText.class::isInstance)
                .map(Message::contentToString)
                .filter(StrUtil::isNotEmpty)
                .peek(stringBuffer::append)
                .count();
        return stringBuffer.toString().trim();
    }

    /**
     * 获取命令后剩余内容
     *
     * @param event
     * @return
     */
    public static Pair<String, String> getCommandPlainText(MessageEvent event) {
        List<PlainText> list = event.getMessage()
                .stream()
                .filter(PlainText.class::isInstance)
                .filter(f -> !StrUtil.isBlank(f.contentToString()))
                .map(m -> (PlainText) m)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(list)) {
            return new Pair<>(null, null);
        }

        StringBuffer sbf = new StringBuffer();

        for (PlainText plainText : list) {
            sbf.append(plainText.contentToString().trim());
        }

        String[] strs = sbf.toString().split(CommonConstant.HSIANG_HSIEN, 2);
        return new Pair<>(strs[0].trim(), strs.length > 1 ? strs[1].trim() : null);
    }


    public static MessageChain buildMessage(MessageEvent event, boolean isQuote, Image image, String revert) {
        MessageChainBuilder singleMessages = new MessageChainBuilder();
        // 引用收到的消息并回复, 也可以添加图片等更多元素.
        if (isQuote) {
            singleMessages.append(new QuoteReply(event.getMessage()));
        }
        if (image != null) {
            singleMessages.append(image);
        }

        if (StrUtil.isNotBlank(revert)) {
            singleMessages.append(new PlainText(revert));
        }
        return singleMessages.build();
    }

    public static void senderQuoteReplyMessage(MessageEvent event, String revert) {
        event.getSubject().sendMessage(buildMessage(event, true, null, revert));
    }

    public static void senderImage(MessageEvent event, String uri) {
        Image image = null;
        try (ExternalResource externalResource = ExternalResource.create(new File(uri));) {
            if (event instanceof GroupMessageEvent) {
                image = ((GroupMessageEvent) event).getGroup().uploadImage(externalResource);
            } else if (event instanceof FriendMessageEvent) {
                image = ((FriendMessageEvent) event).getFriend().uploadImage(externalResource);
            }
            event.getSubject().sendMessage(buildMessage(event, false, image, null));
        } catch (IOException e) {
            String msg = Arrays.toString("error msg: ".getBytes(StandardCharsets.UTF_8)) +e.getMessage();
            LOGGER.error(msg);
            event.getSubject().sendMessage(msg);
        }finally {
            FileUtil.del(new File(uri));
        }
    }
}
