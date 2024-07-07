package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.core.common.MessageTemplate;
import top.aenlly.qqrobot.entity.GroupBlackEntity;
import top.aenlly.qqrobot.entity.GroupConfigEntity;
import top.aenlly.qqrobot.mapper.GroupBlackMapper;
import top.aenlly.qqrobot.mapper.GroupConfigMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 入群处理
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@AllArgsConstructor
@Component
public class MemberJoinRequestEventListener extends SimpleListenerHost {

    @Autowired
    private GroupConfigMapper groupConfigMapper;

    @Autowired
    private GroupBlackMapper groupBlackMapper;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull MemberJoinRequestEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        GroupConfigEntity groupConfigDO = groupConfigMapper.selectOne(GroupConfigEntity::getGroupId, event.getGroupId());
        if(Objects.isNull(groupConfigDO)){
            return;
        }

        if(groupConfigDO.getBlackNumber()>0){
            GroupBlackEntity groupBlackDO = GroupBlackEntity.builder().groupId(event.getGroupId()).blackQQ(event.getInvitorId()).build();
            groupBlackDO = groupBlackMapper.selectOne(groupBlackDO);
            Optional.ofNullable(groupBlackDO).filter(f->f.getNumber()>=groupConfigDO.getBlackNumber()).ifPresent(p->{
                notice(event, groupConfigDO, MessageTemplate.GROUP_BLACK_NOTICE);
            });
        }

        switch (groupConfigDO.getAutoAudit()){
            case AUTO ->
                    event.accept();
            case NOTICE ->{
                notice(event, groupConfigDO, MessageTemplate.GROUP_AUDIT_MESSAGE_NOTICE);
            }
            case NONE -> {}
        }
    }

    private static void notice(@NotNull MemberJoinRequestEvent event, GroupConfigEntity groupConfigDO, MessageTemplate messageTemplate) {
        List<Long> noticeQQ = groupConfigDO.getNoticeQQ();
        if(noticeQQ.isEmpty()){
            return;
        }
        noticeQQ.forEach(f->{
            Friend friend = event.getBot().getFriend(f);
            Optional.ofNullable(friend).ifPresentOrElse(p->p.sendMessage(String.format(messageTemplate.getValue(), event.getGroup().getName(),event.getFromId())),()->{
                    NormalMember normalMember = event.getGroup().get(f);
                    Optional.ofNullable(normalMember).ifPresent(p->p.sendMessage(String.format(messageTemplate.getValue(), event.getGroup().getName(),event.getFromId())));
            });
        });
    }
}
