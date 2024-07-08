package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.BotCommandEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoCommandMapper extends BaseMapperX<BotCommandEntity> {

    default List<BotCommandEntity> selectList(BotCommandEntity botGroupDO) {
        botGroupDO.setMatchType(MatchTypeEnum.EXACT);
        LambdaQueryWrapperX<BotCommandEntity> queryWrapperX = getBotGroupEntityLambdaQueryWrapperX(botGroupDO);
        List<BotCommandEntity> exactList = this.selectList(queryWrapperX);
        // 前缀匹配
        List<BotCommandEntity> prefixList = selectList(queryWrapperX);
        botGroupDO.setMatchType(MatchTypeEnum.PREFIX);
        // 正则表达式
        botGroupDO.setMatchType(MatchTypeEnum.REGEX);
        List<BotCommandEntity> regexList = selectList(queryWrapperX);;
        Optional.ofNullable(prefixList).ifPresent(exactList::addAll);
        Optional.ofNullable(regexList).ifPresent(exactList::addAll);
        return exactList;
    }

    default LambdaQueryWrapperX<BotCommandEntity> getBotGroupEntityLambdaQueryWrapperX(BotCommandEntity botGroupDO) {
        LambdaQueryWrapperX<BotCommandEntity> queryWrapperX = new LambdaQueryWrapperX<BotCommandEntity>()
                .eqIfPresent(BotCommandEntity::getGroupId, botGroupDO.getGroupId())
                .eqIfPresent(BotCommandEntity::getStatus, StatusEnum.ENABLED)
                .eqIfPresent(BotCommandEntity::getAt, botGroupDO.getAt())
                .eqIfPresent(BotCommandEntity::getMatchType, botGroupDO.getMatchType())
                .eqIfPresent(BotCommandEntity::getOptType, botGroupDO.getOptType());
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.EXACT)) {
            queryWrapperX.likeIfPresent(BotCommandEntity::getMatchValue, "," + botGroupDO.getMatchValue() + ",");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.REGEX)) {
            queryWrapperX.apply("  match_value regexp '"+ botGroupDO.getMatchValue()+"'");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.PREFIX)) {
            queryWrapperX.likeRight(BotCommandEntity::getMatchValue, botGroupDO.getMatchValue());
        }
        return queryWrapperX;
    }
}
