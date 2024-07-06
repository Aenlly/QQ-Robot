package top.aenlly.qqrobot.strategy;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ArrayUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.GroupDO;
import top.aenlly.qqrobot.mapper.GroupMapper;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

/**
 * 群策略
 */
public class GroupStrategy implements MessageStrategy{

    private final GroupMapper groupMapper;

    public GroupStrategy(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public void processMessages(MessageEvent event) {
        GroupMessageEvent groupMessageEvent = (GroupMessageEvent) event;
        // 获取群号
        long groupId = groupMessageEvent.getGroup().getId();

        // 获取内容
        List<SingleMessage> plainText = MessageUtils.getPlainText(event);

        String msg = plainText.get(0).contentToString();

        // 获取被艾特对象
        List<Long> ats = MessageUtils.getAt(event);
        if(ats.size()>1){return;}

        GroupDO groupDO = GroupDO.builder()
                .matchType(MatchTypeEnum.EXACT)
                .matchValue(msg.trim())
                .optType(OptTypeEnum.ORDINARY)
                .groupId(groupId)
                .at(Opt.ofNullable(ats).filter(f-> !f.isEmpty()).map(f -> true).orElse(false))
                .status(StatusEnum.ENABLED)
                .optType(OptTypeEnum.ORDINARY).build();

        // 精确匹配
        List<GroupDO> exactList = groupMapper.selectList(groupDO);
        if(ArrayUtil.isNotEmpty(exactList)){
            exactList.forEach(f->{
                event.getSubject().sendMessage(MessageUtils.buildQuoteReplyMessage(event,f.getRevert()));
            });
            return;
        }

        // 前缀匹配
        groupDO.setMatchType(MatchTypeEnum.PREFIX);
        List<GroupDO> prefixList = groupMapper.selectList(groupDO);

        if(ArrayUtil.isNotEmpty(prefixList)){
            prefixList.forEach(f->{
                event.getSubject().sendMessage(MessageUtils.buildQuoteReplyMessage(event,f.getRevert()));
            });
            return;
        }

        // 正则表达式
        groupDO.setMatchType(MatchTypeEnum.REGEX);
        List<GroupDO> regexList = groupMapper.selectList(groupDO);;
        if(ArrayUtil.isNotEmpty(regexList)){
            regexList.forEach(f->{
                event.getSubject().sendMessage(MessageUtils.buildQuoteReplyMessage(event,f.getRevert()));
            });
        }


    }

    @Override
    public MessageSourceKind getMessageSourceKind() {
        return MessageSourceKind.GROUP;
    }
}
