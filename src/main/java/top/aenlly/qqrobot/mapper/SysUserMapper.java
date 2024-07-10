package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.StatusEnum;
import top.aenlly.qqrobot.entity.SysUserEntity;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.time.LocalDateTime;

@Mapper
public interface SysUserMapper extends BaseMapperX<SysUserEntity> {

    default SysUserEntity selectOne(Long qq){
        return this.selectOne(new LambdaQueryWrapperX<SysUserEntity>().eq(SysUserEntity::getQq,qq )
                .eq(SysUserEntity::getStatus, StatusEnum.ENABLED)
                .ge(SysUserEntity::getExpiredTime, LocalDateTime.now()));
    }
}
