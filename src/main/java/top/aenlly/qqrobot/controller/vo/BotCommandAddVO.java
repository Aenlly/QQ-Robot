package top.aenlly.qqrobot.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.aenlly.qqrobot.enmus.MatchTypeEnum;
import top.aenlly.qqrobot.enmus.OptTypeEnum;
import top.aenlly.qqrobot.enmus.StatusEnum;

import java.util.List;

@Data
@NoArgsConstructor
public class BotCommandAddVO extends BaseVO{

    private Long groupId;

    /**
     * 匹配类型
     */
    private MatchTypeEnum matchType;

    /**
     * 匹配值
     */
    private List<String> matchValue;
    /**
     * 回复
     */
    private String revert;

    /**
     * 是否需要艾特机器人
     */
    private Boolean at;
    /**
     * 机器人操作
     */
    private OptTypeEnum optType;

    /**
     * 是否启用
     */
    private StatusEnum status;

    /**
     * 命令
     */
    private String command;

}
