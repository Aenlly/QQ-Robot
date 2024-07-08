package top.aenlly.qqrobot.adapter.command.shoot;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import net.mamoe.mirai.event.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.adapter.command.AbstractCommand;
import top.aenlly.qqrobot.constant.CommonConstant;
import top.aenlly.qqrobot.core.common.MessageTemplate;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.enmus.ShootOrderStatusEnum;
import top.aenlly.qqrobot.entity.ShootOrderEntity;
import top.aenlly.qqrobot.entity.UserGroupEntity;
import top.aenlly.qqrobot.mapper.ShootOrderMapper;
import top.aenlly.qqrobot.mapper.UserGroupMapper;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ShootFinalMoney extends AbstractCommand {

    @Autowired
    private ShootOrderMapper shootOrderMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public String getName() {
        return CommandEnum.SHOOT_FINAL_MONEY.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        command();
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        command();
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        command();
    }

    private void command() {
        long id = event.getSender().getId();
        List<UserGroupEntity> userGroupEntities = userGroupMapper.selectList(new LambdaQueryWrapperX<UserGroupEntity>().eq(UserGroupEntity::getAdminQQ, id).in(UserGroupEntity::getRoleType, RoleTypeEnum.ALL, RoleTypeEnum.QUERY));
        LambdaQueryWrapperX<ShootOrderEntity> queryWrapperX = new LambdaQueryWrapperX<>();
        if (CollUtil.isNotEmpty(userGroupEntities)) {
            queryWrapperX.eqIfPresent(ShootOrderEntity::getAdminQQ, id);
        } else {
            queryWrapperX.eqIfPresent(ShootOrderEntity::getQq, id);
        }
        queryWrapperX.notIn(ShootOrderEntity::getStatus, ShootOrderStatusEnum.END_OF_PROCESS);
        List<ShootOrderEntity> shootOrderEntities = shootOrderMapper.selectList(queryWrapperX);
        Map<Integer, List<ShootOrderEntity>> listMap = shootOrderEntities.stream().collect(Collectors.groupingBy(ShootOrderEntity::getQq));
        listMap.forEach((k, v) -> {
            AtomicInteger allMoney = new AtomicInteger();
            AtomicInteger deposit = new AtomicInteger();
            AtomicInteger finalMoney = new AtomicInteger();
            List<String> times = new ArrayList<>();
            v.forEach(f -> {
                deposit.getAndAdd(f.getDeposit());
                allMoney.getAndAdd(f.getDeposit());
                if (f.getStatus().equals(ShootOrderStatusEnum.WAITING_FOR_THE_TAIL)) {
                    finalMoney.getAndAdd(f.getFinalMoney());
                    times.add(LocalDateTimeUtil.format(f.getShootTime(), DatePattern.NORM_DATE_PATTERN) + CommonConstant.SPACE + f.getTimeInterval());
                }
            });
            event.getSubject().sendMessage(String.format(MessageTemplate.SHOOT_ORDER_FINAL.getValue(), k, allMoney.get(), deposit.get(), finalMoney.get(), JSONUtil.parse(times).toJSONString(2)));
        });
    }
}
