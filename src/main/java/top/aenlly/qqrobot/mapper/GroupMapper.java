package top.aenlly.qqrobot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.entity.GroupDO;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;
import top.aenlly.qqrobot.mapper.query.QueryWrapperX;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapperX<GroupDO> {

    default List<GroupDO> selectList(GroupDO groupDO) {
        LambdaQueryWrapperX<GroupDO> queryWrapperX = new LambdaQueryWrapperX<GroupDO>()
                .eqIfPresent(GroupDO::getGroupId, groupDO.getGroupId())
                .eqIfPresent(GroupDO::getStatus, groupDO.getStatus())
                .eqIfPresent(GroupDO::getAt, groupDO.getAt())
                .eqIfPresent(GroupDO::getMatchType, groupDO.getMatchType())
                .eqIfPresent(GroupDO::getOptType, groupDO.getOptType());
        if (groupDO.getMatchType().equals(MatchTypeEnum.EXACT)) {
            queryWrapperX.likeIfPresent(GroupDO::getMatchValue, "," + groupDO.getMatchValue() + ",");
        }
        if (groupDO.getMatchType().equals(MatchTypeEnum.REGEX)) {
            queryWrapperX.apply(" match_value regexp", groupDO.getMatchValue());
        }
        if (groupDO.getMatchType().equals(MatchTypeEnum.PREFIX)) {
            queryWrapperX.likeRight(GroupDO::getMatchValue, groupDO.getMatchValue());
        }

        return this.selectList(queryWrapperX);
    }
}
