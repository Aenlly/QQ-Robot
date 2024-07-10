package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.constant.CommonConstant;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.MsgCode;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.SysUserEntity;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.SysUserMapper;
import top.aenlly.qqrobot.mapper.UserRoleMapper;

import java.util.List;
import java.util.Locale;

@Component
public class Role extends AbstractCommand {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getName() {
        return CommandEnum.ROLE.name();
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        SysUserEntity sysUserEntity = sysUserMapper.selectOne(event.getSender().getId());

        if (ObjectUtil.isEmpty(sysUserEntity)) {
            sendPermission();
            return;
        }
        String[] params = context.getContent().split(CommonConstant.SPACE);
        if (params.length != 4) {
            send(MsgCode.PARAMS_ERROR_2.getMsg());
            return;
        }
        try {
            switch (params[0].trim().toLowerCase(Locale.ROOT)) {
                case "add", "-a": {
                    Assert.notBlank(params[2]);
                    Assert.notBlank(params[3]);
                    userRoleMapper.insert(UserRoleEntity.builder()
                            .groupId(Opt.ofNullable(params[1]).filter(StrUtil::isNotBlank).map(Long::valueOf).orElse(null))
                            .adminQQ(Long.valueOf(params[2].trim()))
                            .roleType(RoleTypeEnum.valueOf(params[3].trim().toUpperCase(Locale.ROOT)))
                            .build());
                    send(MsgCode.OPT_SUCCESS.getMsg());
                }
                case "del", "-d": {
                    Assert.notBlank(params[2]);
                    Assert.notBlank(params[3]);
                    userRoleMapper.deleteByEntity(UserRoleEntity.builder()
                            .groupId(Opt.ofNullable(params[1]).filter(StrUtil::isNotBlank).map(Long::valueOf).orElse(null))
                            .adminQQ(Long.valueOf(params[2].trim()))
                            .roleType(RoleTypeEnum.valueOf(params[3].trim().toUpperCase(Locale.ROOT)))
                            .build());
                    send(MsgCode.OPT_SUCCESS.getMsg());
                }
                case "get","-g":{
                    List<UserRoleEntity> userRoleEntities = userRoleMapper.selectList(UserRoleEntity::getAdminQQ, params[1]);
                    send(JSONUtil.toJsonStr(userRoleEntities));
                }
            }
        } catch (Exception e) {
            send(MsgCode.PARAMS_ERROR_2.getMsg());
        }

        super.execute(event);
    }
}
