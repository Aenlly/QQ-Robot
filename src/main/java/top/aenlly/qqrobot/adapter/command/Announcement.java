package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.announcement.Announcements;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.UserRoleMapper;

import java.util.Arrays;
import java.util.List;

@Component
public class Announcement extends AbstractCommand {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getName() {
        return CommandEnum.ANNOUNCEMENT.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        Group group = event.getGroup();
        UserRoleEntity query = UserRoleEntity.builder().adminQQ(event.getSender().getId())
                .groupId(group.getId()).build();
        List<UserRoleEntity> userGroupEntities = userRoleMapper.queryUserRole(query,
                Arrays.asList(RoleTypeEnum.ALL, RoleTypeEnum.ANNOUNCEMENT));
        if (CollUtil.isEmpty(userGroupEntities)) {
            sendPermission();
            return;
        }
        Announcements announcements = group.getAnnouncements();

        super.execute(event);
    }
}
