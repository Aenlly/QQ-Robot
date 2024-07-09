package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.BotGroupCommandEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BotGroupCommandMapper extends BaseMapperX<BotGroupCommandEntity> {

    default List<BotGroupCommandEntity> selectList(BotGroupCommandEntity botGroupDO) {
        botGroupDO.setMatchType(MatchTypeEnum.EXACT);
        LambdaQueryWrapperX<BotGroupCommandEntity> queryWrapperX = getBotGroupEntityLambdaQueryWrapperX(botGroupDO);
        List<BotGroupCommandEntity> exactList = this.selectList(queryWrapperX);
        // 前缀匹配
        List<BotGroupCommandEntity> prefixList = selectList(queryWrapperX);
        botGroupDO.setMatchType(MatchTypeEnum.PREFIX);
        // 正则表达式
        botGroupDO.setMatchType(MatchTypeEnum.REGEX);
        List<BotGroupCommandEntity> regexList = selectList(queryWrapperX);;
        Optional.ofNullable(prefixList).ifPresent(exactList::addAll);
        Optional.ofNullable(regexList).ifPresent(exactList::addAll);
        return exactList;
    }

    default LambdaQueryWrapperX<BotGroupCommandEntity> getBotGroupEntityLambdaQueryWrapperX(BotGroupCommandEntity botGroupDO) {
        LambdaQueryWrapperX<BotGroupCommandEntity> queryWrapperX = new LambdaQueryWrapperX<BotGroupCommandEntity>()
                .eqIfPresent(BotGroupCommandEntity::getGroupId, botGroupDO.getGroupId())
                .eqIfPresent(BotGroupCommandEntity::getStatus, StatusEnum.ENABLED)
                .eqIfPresent(BotGroupCommandEntity::getAt, botGroupDO.getAt())
                .eqIfPresent(BotGroupCommandEntity::getMatchType, botGroupDO.getMatchType())
                .eqIfPresent(BotGroupCommandEntity::getOptType, botGroupDO.getOptType());
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.EXACT)) {
            queryWrapperX.likeIfPresent(BotGroupCommandEntity::getMatchValue, "," + botGroupDO.getMatchValue() + ",");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.REGEX)) {
            queryWrapperX.apply("  match_value regexp '"+ botGroupDO.getMatchValue()+"'");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.PREFIX)) {
            queryWrapperX.likeRight(BotGroupCommandEntity::getMatchValue, botGroupDO.getMatchValue());
        }
        return queryWrapperX;
    }
}
