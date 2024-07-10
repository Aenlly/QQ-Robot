package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.constant.CommonConstant;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.UserRoleMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 禁言
 *
 * @author Aenlly||tnw
 * @create 2024/07/10 19:40:51
 * @since 1.0.0
 */
@Component
public class Mute extends AbstractCommand {


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getName() {
        return CommandEnum.MUTE.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        UserRoleEntity query = UserRoleEntity.builder()
                .adminQQ(event.getSender().getId())
                .groupId(event.getGroup().getId()).build();
        List<UserRoleEntity> userGroupEntities = userRoleMapper.queryUserRole(query, Arrays.asList(RoleTypeEnum.ALL, RoleTypeEnum.MUTE));

        if (CollUtil.isEmpty(userGroupEntities)) {
            sendPermission();
            return;
        }



        List<Long> ats = MessageUtils.getAt(event).stream().filter(f -> !f.equals(event.getBot().getId())).toList();
        if(CollUtil.isEmpty(ats)){
            event.getGroup().getSettings().setMuteAll(!event.getGroup().getSettings().isMuteAll());
            return;
        }
        for (Long at : ats) {
            NormalMember member = event.getGroup()
                    .getMembers()
                    .get(at);
            if (member == null) {
                break;
            }
            if (member.isMuted()) {
                member.unmute();
                break;
            }
            member.mute(StrUtil.isBlank(context.getContent()) ? Integer.parseInt(context.getContent()) : CommonConstant.BAN_DEFAULT);

        }
        super.execute(event);
    }
}
