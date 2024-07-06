package top.aenlly.qqrobot.strategy;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextStrategy {
    private final Map<MessageSourceKind,MessageStrategy> CONTEXT_HOLDER =new HashMap<>(8);

    public ContextStrategy(List<MessageStrategy> messageStrategies) {
        messageStrategies.forEach(f->CONTEXT_HOLDER.put(f.getMessageSourceKind(),f));
    }

    public void processMessage(MessageSourceKind messageSourceKind, MessageEvent event){
        CONTEXT_HOLDER.get(messageSourceKind).processMessages(event);
    }
}
