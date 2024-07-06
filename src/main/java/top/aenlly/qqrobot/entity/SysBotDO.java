package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_bot",autoResultMap = true)
public class SysBotDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("qq")
    private Long qq;

    /**
     * 匹配类型
     */
    @TableField("password")
    private String password;

    /**
     * 是否启用
     */
    @TableField("`status`")
    private StatusEnum status;
    /**
     * 是否忽略大小写
     */
    @TableField("ignore_case")
    private boolean ignoreCase;


}
