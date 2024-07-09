package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import top.aenlly.qqrobot.enmus.RoleTypeEnum;

@Data
@Builder
@TableName(value = "user_role",autoResultMap = true)
public class UserRoleEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long groupId;

    /**
     *
     */
    @TableField("admin_qq")
    private Long adminQQ;

    /**
     * 权限
     */
    private RoleTypeEnum roleType;

}
