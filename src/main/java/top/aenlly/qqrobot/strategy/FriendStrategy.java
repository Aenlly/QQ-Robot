package top.aenlly.qqrobot.strategy;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;

/**
 * 好友策略
 */
public class FriendStrategy implements MessageStrategy{
    @Override
    public void processMessages(MessageEvent event) {

    }

    @Override
    public MessageSourceKind getMessageSourceKind() {
        return MessageSourceKind.FRIEND;
    }
}
