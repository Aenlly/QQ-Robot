package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.constant.CommonConstant;
import top.aenlly.qqrobot.enmus.*;
import top.aenlly.qqrobot.entity.BotGroupCommandEntity;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.BotGroupCommandMapper;
import top.aenlly.qqrobot.mapper.UserRoleMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CommandAdd extends AbstractCommand {

    @Autowired
    private BotGroupCommandMapper botGroupCommandMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String getName() {
        return CommandEnum.COMMAND_ADD.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        add(event.getGroup().getId());
    }

    @Override
    protected void execute(FriendMessageEvent event) {
        add(null);
    }

    @Override
    protected void execute(GroupTempMessageEvent event) {
        add(event.getGroup().getId());
    }

    private boolean add(Long groupId) {
        UserRoleEntity query = UserRoleEntity.builder().adminQQ(event.getSender().getId())
                .groupId(groupId).build();
        List<UserRoleEntity> userGroupEntities = userRoleMapper.queryUserRole(query, Arrays.asList(RoleTypeEnum.ALL, RoleTypeEnum.ADD));

        if (CollUtil.isEmpty(userGroupEntities)) {
            sendPermission();
            return true;
        }

        Pair<String, String> pair = MessageUtils.getCommandPlainText(event);
        String[] strs = pair.getValue().split(CommonConstant.HSIANG_HSIEN,2);

        String str = strs[0];

        switch (Integer.parseInt(str)){
            case 2->
                    this.add2(strs[1]);
            case 3->
                    this.add3(strs[1]);
            case 4->
                    this.add4(strs[1]);
            case 7->
                    this.add7(strs[1]);
            case 8->
                    this.add8(strs[1]);
        }
        return false;
    }

    private void add2(String str){
        String[]  strs= str.split(CommonConstant.HSIANG_HSIEN, 2);

        this.add(MatchTypeEnum.EXACT,strs[0],strs[1],false,OptTypeEnum.ORDINARY,StatusEnum.ENABLED,null,false);
    }

    private void add3(String str){
        String[]  strs= str.split(CommonConstant.HSIANG_HSIEN, 2);
        MatchTypeEnum matchType;
        try {
            matchType = MatchTypeEnum.valueOf(strs[0]);
        }catch (Exception e){
            event.getSubject().sendMessage(String.format(MsgCode.PARAMS_ERROR_1.getMsg(),strs[0], JSONUtil.toJsonStr(MatchTypeEnum.values())));
            log.error("error code :{}" , MsgCode.PARAMS_ERROR_1.getCode(),e.fillInStackTrace());
            return;
        }
        this.add(matchType,strs[1],strs[2],false,OptTypeEnum.ORDINARY,StatusEnum.ENABLED,null,false);
    }

    private void add4(String str){
        String[]  strs= str.split(CommonConstant.HSIANG_HSIEN, 2);
        MatchTypeEnum matchType;
        try {
            matchType = MatchTypeEnum.valueOf(strs[0]);
        }catch (Exception e){
            event.getSubject().sendMessage(String.format(MsgCode.PARAMS_ERROR_1.getMsg(),strs[0], JSONUtil.toJsonStr(MatchTypeEnum.values())));
            log.error("error code :{}" , MsgCode.PARAMS_ERROR_1.getCode(),e.fillInStackTrace());
            return;
        }
        this.add(matchType,strs[1],strs[3],false,OptTypeEnum.COMMAND,StatusEnum.ENABLED,strs[2],false);
    }

    private void add7(String str){
        String[]  strs= str.split(CommonConstant.HSIANG_HSIEN, 2);
        try {
            MatchTypeEnum matchType = MatchTypeEnum.valueOf(strs[0]);
            boolean at = Boolean.parseBoolean(strs[2]);
            OptTypeEnum optType = OptTypeEnum.valueOf(strs[3]);
            StatusEnum status = StatusEnum.valueOf(strs[4]);
            boolean ignoreCase = Boolean.parseBoolean(strs[5]);

            this.add(matchType,strs[1],strs[6],at,optType,status,null,ignoreCase);
        }catch (Exception e){
            event.getSubject().sendMessage(String.format(MsgCode.PARAMS_ERROR_2.getMsg(),strs[0], JSONUtil.toJsonStr(MatchTypeEnum.values())));
            log.error("error code :{}" , MsgCode.PARAMS_ERROR_2.getCode(),e.fillInStackTrace());
        }
    }

    private void add8(String str){
        String[]  strs= str.split(CommonConstant.HSIANG_HSIEN, 2);
        try {
            MatchTypeEnum matchType = MatchTypeEnum.valueOf(strs[0]);
            boolean at = Boolean.parseBoolean(strs[2]);
            OptTypeEnum optType = OptTypeEnum.valueOf(strs[3]);
            StatusEnum status = StatusEnum.valueOf(strs[4]);
            boolean ignoreCase = Boolean.parseBoolean(strs[6]);
            this.add(matchType,strs[1],strs[7],at,optType,status,strs[5],ignoreCase);
        }catch (Exception e){
            event.getSubject().sendMessage(String.format(MsgCode.PARAMS_ERROR_2.getMsg()));
            log.error("error code :{}" , MsgCode.PARAMS_ERROR_2.getCode(),e.fillInStackTrace());
        }
    }

    public void add(MatchTypeEnum matchType, String matchValue, String revert, Boolean at, OptTypeEnum optType, StatusEnum status, String command, boolean ignoreCase) {
        BotGroupCommandEntity entity = BotGroupCommandEntity.builder()
                .groupId(((GroupMessageEvent)event).getGroup().getId())
                .matchType(matchType)
                .matchValue(CommonConstant.COMMA + matchValue + CommonConstant.COMMA)
                .revert(revert)
                .at(at)
                .optType(optType)
                .status(status)
                .command(command)
                .ignoreCase(ignoreCase)
                .build();
        botGroupCommandMapper.insert(entity);

        MessageChain msg = new MessageChainBuilder().append(MsgCode.OPT_SUCCESS.getMsg()).append("\n").append(JSONUtil.toJsonStr(entity)).build();
        event.getSubject().sendMessage(msg);
    }
}
