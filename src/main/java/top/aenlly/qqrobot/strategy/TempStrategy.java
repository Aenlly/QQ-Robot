package top.aenlly.qqrobot.strategy;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;

/**
 * 群临时会话策略
 */
public class TempStrategy implements MessageStrategy{

    @Override
    public void processMessages(MessageEvent event) {

    }

    @Override
    public MessageSourceKind getMessageSourceKind() {
        return MessageSourceKind.STRANGER;
    }
}

