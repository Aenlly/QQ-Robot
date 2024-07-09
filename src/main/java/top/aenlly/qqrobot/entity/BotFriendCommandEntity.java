package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "bot_friend_command",autoResultMap = true)
public class BotFriendCommandEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_id")
    private Long friendId;

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
     * 是否启用
     */
    @TableField("`status`")
    private StatusEnum status;

    /**
     * 命令
     */
    @TableField("`command`")
    private String command;

}
