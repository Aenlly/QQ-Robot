package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.entity.GroupBlackEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

@Mapper
public interface GroupBlackMapper extends BaseMapperX<GroupBlackEntity> {

    default GroupBlackEntity selectOne(GroupBlackEntity groupBlackDO){
     return this.selectOne(new LambdaQueryWrapperX<GroupBlackEntity>().eq(GroupBlackEntity::getGroupId,groupBlackDO.getGroupId()).eq(GroupBlackEntity::getBlackQQ,groupBlackDO.getBlackQQ()));
    }
}
