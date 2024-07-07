package top.aenlly.qqrobot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.entity.GroupBlackEntity;
import top.aenlly.qqrobot.mapper.GroupBlackMapper;

import java.util.Optional;

/**
 * 群退出、踢出事件
 * @author Aenlly||tnw
 * @create 2024/06/06 18:02
 * @since 1.0.0
 */
@Component
public class MemberLeaveEventListener extends SimpleListenerHost {

    @Autowired
    private GroupBlackMapper groupBlackMapper;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
    }

    @EventHandler
    public void onMessage(@NotNull MemberLeaveEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        Member member = event.getMember();
        Group group = event.getGroup();
        GroupBlackEntity groupBlackDO = GroupBlackEntity.builder().blackQQ(group.getId()).blackQQ(member.getId()).build();
        GroupBlackEntity groupBlackDO1 = groupBlackMapper.selectOne(groupBlackDO);
        Optional.ofNullable(groupBlackDO1).filter(f->f.getId()!=null).ifPresentOrElse(p->{
            p.setNumber(p.getNumber()+1);
            groupBlackMapper.updateById(groupBlackDO1);
        },()->groupBlackMapper.insert(groupBlackDO));
    }
}
