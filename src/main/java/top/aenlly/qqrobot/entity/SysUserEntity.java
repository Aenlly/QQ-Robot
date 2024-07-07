package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.aenlly.qqrobot.enmus.StatusEnum;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user",autoResultMap = true)
public class SysUserEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("qq")
    private Long qq;


    @TableField("tel")
    private String tel;

    /**
     * 匹配类型
     */
    @TableField("expired_time")
    private LocalDateTime expiredTime;

    /**
     * 是否启用
     */
    @TableField("`status`")
    private StatusEnum status;

}
