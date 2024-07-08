package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "bot_command",autoResultMap = true)
public class BotCommandEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("group_id")
    private Long groupId;

    /**
     * 匹配类型
     */
    @TableField("match_type")
    private MatchTypeEnum matchType;

    /**
     * 匹配值
     */
    @TableField("match_value")
    private String matchValue;
    /**
     * 回复
     */
    @TableField("revert")
    private String revert;

    /**
     * 是否需要艾特机器人
     */
    @TableField("`at`")
    private Boolean at;
    /**
     * 机器人操作
     */
    private OptTypeEnum optType;

    /**
     * 是否启用
     */
    @TableField("`status`")
    private StatusEnum status;

    /**
     * 命令
     */
    @TableField("`command`")
    private String command;

    /**
     * 是否忽略大小写
     */
    @TableField("ignore_case")
    private boolean ignoreCase;

}
