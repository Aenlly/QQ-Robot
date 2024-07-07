package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.entity.BotGroupEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapperX<BotGroupEntity> {

    default List<BotGroupEntity> selectList(BotGroupEntity botGroupDO) {
        LambdaQueryWrapperX<BotGroupEntity> queryWrapperX = new LambdaQueryWrapperX<BotGroupEntity>()
                .eqIfPresent(BotGroupEntity::getGroupId, botGroupDO.getGroupId())
                .eqIfPresent(BotGroupEntity::getStatus, botGroupDO.getStatus())
                .eqIfPresent(BotGroupEntity::getAt, botGroupDO.getAt())
                .eqIfPresent(BotGroupEntity::getMatchType, botGroupDO.getMatchType())
                .eqIfPresent(BotGroupEntity::getOptType, botGroupDO.getOptType());
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.EXACT)) {
            queryWrapperX.likeIfPresent(BotGroupEntity::getMatchValue, "," + botGroupDO.getMatchValue() + ",");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.REGEX)) {
            queryWrapperX.apply("  match_value regexp '"+botGroupDO.getMatchValue()+"'");
        }
        if (botGroupDO.getMatchType().equals(MatchTypeEnum.PREFIX)) {
            queryWrapperX.likeRight(BotGroupEntity::getMatchValue, botGroupDO.getMatchValue());
        }

        return this.selectList(queryWrapperX);
    }
}
