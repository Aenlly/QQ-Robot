package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.BotFriendCommandEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BotFriendCommandMapper extends BaseMapperX<BotFriendCommandEntity> {

    default List<BotFriendCommandEntity> selectList(BotFriendCommandEntity botGroupDO) {
        botGroupDO.setMatchType(MatchTypeEnum.EXACT);
        LambdaQueryWrapperX<BotFriendCommandEntity> queryWrapperX = getBotGroupEntityLambdaQueryWrapperX(botGroupDO);
        List<BotFriendCommandEntity> exactList = this.selectList(queryWrapperX);
        // 前缀匹配
        List<BotFriendCommandEntity> prefixList = selectList(queryWrapperX);
        botGroupDO.setMatchType(MatchTypeEnum.PREFIX);
        // 正则表达式
        botGroupDO.setMatchType(MatchTypeEnum.REGEX);
        List<BotFriendCommandEntity> regexList = selectList(queryWrapperX);;
        Optional.ofNullable(prefixList).ifPresent(exactList::addAll);
        Optional.ofNullable(regexList).ifPresent(exactList::addAll);
        return exactList;
    }

    default LambdaQueryWrapperX<BotFriendCommandEntity> getBotGroupEntityLambdaQueryWrapperX(BotFriendCommandEntity botGroupDO) {
        LambdaQueryWrapperX<BotFriendCommandEntity> queryWrapperX = new LambdaQueryWrapperX<BotFriendCommandEntity>()
                .eqIfPresent(BotFriendCommandEntity::getFriendId, botGroupDO.getFriendId())
                .eqIfPresent(BotFriendCommandEntity::getStatus, StatusEnum.ENABLED)
                .eqIfPresent(BotFriendCommandEntity::getMatchType, botGroupDO.getMatchType());
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.EXACT)) {
            queryWrapperX.likeIfPresent(BotFriendCommandEntity::getMatchValue, "," + botGroupDO.getMatchValue() + ",");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.REGEX)) {
            queryWrapperX.apply("  match_value regexp '"+ botGroupDO.getMatchValue()+"'");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.PREFIX)) {
            queryWrapperX.likeRight(BotFriendCommandEntity::getMatchValue, botGroupDO.getMatchValue());
        }
        return queryWrapperX;
    }
}
