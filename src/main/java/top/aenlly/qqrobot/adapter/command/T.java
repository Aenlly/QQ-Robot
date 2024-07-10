package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.UserRoleMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class T extends AbstractCommand  {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public String getName() {
        return CommandEnum.T.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        UserRoleEntity query = UserRoleEntity.builder().adminQQ(event.getSender().getId())
            .groupId(event.getGroup().getId()).build();
        List<UserRoleEntity> userGroupEntities = userRoleMapper.queryUserRole(query, Arrays.asList(RoleTypeEnum.ALL, RoleTypeEnum.T));

        if (CollUtil.isEmpty(userGroupEntities)) {
            sendPermission();
            return ;
        }
        List<Long> ats = MessageUtils.getAt(event);
        List<Long> optAt = ats.stream().filter(f -> !f.equals(event.getBot().getId())).toList();
        Group group = event.getGroup();
        if(ArrayUtil.isNotEmpty(optAt)){
            for (Long aLong : optAt){
                optAt.forEach(f-> group.get(f).kick(String.valueOf(aLong)));
                log.info("group {} kick {}",group.getId(),optAt);
            }
        }
    }
}
