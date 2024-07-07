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
@TableName(value = "user_group",autoResultMap = true)
public class UserGroupEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("group_id")
    private Long groupId;

    /**
     *
     */
    @TableField("admin_qq")
    private Long adminQQ;

    /**
     * 权限
     */
    @TableField("role_type")
    private RoleTypeEnum roleType;

}
