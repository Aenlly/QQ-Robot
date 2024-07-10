package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.MsgCode;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.BotGroupCommandEntity;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.BotGroupCommandMapper;
import top.aenlly.qqrobot.mapper.UserRoleMapper;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class CommandDel extends AbstractCommand{

    @Autowired
    private BotGroupCommandMapper botGroupCommandMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getName() {
        return CommandEnum.COMMAND_DEL.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        del(event.getGroup().getId());
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        del(null);
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        del(event.getGroup().getId());
    }

    private void del(Long groupId) {
        UserRoleEntity query = UserRoleEntity.builder().adminQQ(event.getSender().getId())
                .groupId(groupId).build();
        List<UserRoleEntity> userGroupEntities = userRoleMapper.queryUserRole(query, Arrays.asList(RoleTypeEnum.ALL, RoleTypeEnum.DELETE));

        if (CollUtil.isEmpty(userGroupEntities)) {
            sendPermission();
            return;
        }

        int delete = botGroupCommandMapper.delete(new LambdaQueryWrapperX<BotGroupCommandEntity>().like(BotGroupCommandEntity::getMatchValue,
                context.getContent().trim()));
        if(delete>0){
            MessageUtils.senderQuoteReplyMessage(event, MsgCode.OPT_SUCCESS.getMsg());
        }
    }
}
