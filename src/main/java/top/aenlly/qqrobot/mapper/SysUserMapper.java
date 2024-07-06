package top.aenlly.qqrobot.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.entity.GroupDO;
import top.aenlly.qqrobot.entity.SysUserDO;
import top.aenlly.qqrobot.mapper.query.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapperX<SysUserDO> {
}
