package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;
import top.aenlly.qqrobot.entity.UserRoleEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRoleEntity> {


    default List<UserRoleEntity> queryUserRole(UserRoleEntity userRoleEntity, List<RoleTypeEnum> roleTypeEnums) {
        return this.selectList(new LambdaQueryWrapperX<UserRoleEntity>()
                .eq(UserRoleEntity::getAdminQQ, userRoleEntity.getAdminQQ())
                .eqIfPresent(UserRoleEntity::getGroupId, userRoleEntity.getGroupId())
                .in(UserRoleEntity::getRoleType, roleTypeEnums));
    }

    default int deleteByEntity(UserRoleEntity userRoleEntity){
        return this.delete(new LambdaQueryWrapperX<UserRoleEntity>()
                .eq(UserRoleEntity::getAdminQQ, userRoleEntity.getAdminQQ())
                .eqIfPresent(UserRoleEntity::getGroupId, userRoleEntity.getGroupId())
                .in(UserRoleEntity::getRoleType, userRoleEntity.getRoleType()));
    }
}
