package top.aenlly.qqrobot.adapter.command.ai;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.util.StrUtil;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.adapter.command.AbstractCommand;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.tps.baidu.*;
import top.aenlly.qqrobot.tps.baidu.properties.Baidu;
import top.aenlly.qqrobot.utils.MessageUtils;

@ConditionalOnProperty(prefix = "aenlly.ai",value = "ernieSpeed128k",havingValue = "true")
@Component
public class Ernie_Speed_128K extends AbstractCommand implements AI{

    @Autowired
    private Baidu baidu;

    public LRUCache<String, AIReqVO> AI_MSG_LOG =new LRUCache<>(8);

    @Override
    public String getName() {
        return CommandEnum.ERNIE_SPEED_128K.name();
    }

    @Override
    public void executeAI() {
        String content = MessageUtils.getMessageStr(event);
        if(StrUtil.isEmpty(content)){
            return;
        }
        String key = event.getSource().getKind().name() + event.getSender().getId();
        AIReqVO aiReqVO = AI_MSG_LOG.get(key, AIReqVO::new);
        AIReqVO.Message user = new AIReqVO.Message();
        user.setRole(AIRoleEnum.user.name());
        user.setContent(content);
        aiReqVO.getMessages().add(user);
        String token = BaiduTokenUtils.getToken(baidu);
        AIRspVO result = BaiduTokenUtils.post(aiReqVO, AIRspVO.class, BaiduConstant.ERNIE_SPEED_128K + "?access_token=" + token);
        if (result.getError_code() != null) {
            AI_MSG_LOG.remove(key);
            MessageUtils.senderQuoteReplyMessage(event,result.getError_msg()+"\n"+"token已刷新，请稍后再试！");
        } else {
            MessageUtils.senderQuoteReplyMessage(event,result.getResult());
            AIReqVO.Message assistant = new AIReqVO.Message();
            assistant.setRole(AIRoleEnum.assistant.name());
            assistant.setContent(result.getResult());
            aiReqVO.getMessages().add(assistant);
        }
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        executeAI();
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        executeAI();
    }

    @Override
    public boolean getOpenContinuedCommand() {
        return true;
    }
}
