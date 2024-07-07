package top.aenlly.qqrobot.strategy;

import cn.hutool.core.util.ArrayUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.message.data.PlainText;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.constant.CommonConstant;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.BotGroupEntity;
import top.aenlly.qqrobot.mapper.GroupMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 群策略
 */
public class GroupStrategy implements MessageStrategy{

    private final GroupMapper groupMapper;


    private final Map<String, Command> commandHolder;

    public GroupStrategy(GroupMapper groupMapper,List<Command> commands) {
        this.groupMapper = groupMapper;
        commandHolder = commands.stream().collect(Collectors.toMap(Command::getName, c->c));
    }

    @Override
    public void processMessages(MessageEvent event) {
        GroupMessageEvent groupMessageEvent = (GroupMessageEvent) event;
        // 获取群号
        long groupId = groupMessageEvent.getGroup().getId();

        // 获取内容
        List<PlainText> plainText = MessageUtils.getCommandPlainText(event);
        String[] strs = plainText.get(0).contentToString().split(CommonConstant.HSIANG_HSIEN, 2);
        String msg = strs[0];

        // 获取被艾特对象,判断是否存在机器人
        List<Long> ats = MessageUtils.getAt(event);
        long count = ats.stream().filter(f -> f.equals(event.getBot().getId())).count();

        BotGroupEntity query = BotGroupEntity.builder()
                .matchType(MatchTypeEnum.EXACT)
                .matchValue(msg.trim())
                .groupId(groupId)
                .at(count>0)
                .status(StatusEnum.ENABLED)
                .build();

        // 精确匹配
        List<BotGroupEntity> exactList = groupMapper.selectList(query);
        // 前缀匹配
        query.setMatchType(MatchTypeEnum.PREFIX);
        List<BotGroupEntity> prefixList = groupMapper.selectList(query);
        // 正则表达式
        query.setMatchType(MatchTypeEnum.REGEX);
        List<BotGroupEntity> regexList = groupMapper.selectList(query);;
        Optional.ofNullable(prefixList).ifPresent(exactList::addAll);
        Optional.ofNullable(regexList).ifPresent(exactList::addAll);


        if(ArrayUtil.isNotEmpty(exactList)){
            BotGroupEntity botGroupDO = exactList.get(0);
            if(OptTypeEnum.ORDINARY.equals(botGroupDO.getOptType())){
                event.getSubject().sendMessage(MessageUtils.buildQuoteReplyMessage(event,botGroupDO.getRevert()));
                return;
            }
            commandHolder.get(botGroupDO.getCommand()).execute(event);
        }
    }

    @Override
    public MessageSourceKind getMessageSourceKind() {
        return MessageSourceKind.GROUP;
    }
}
