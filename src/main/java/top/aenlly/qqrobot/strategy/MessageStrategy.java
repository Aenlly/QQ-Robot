package top.aenlly.qqrobot.strategy;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;

public interface MessageStrategy {

    void processMessages(MessageEvent event);

    MessageSourceKind getMessageSourceKind();
}
