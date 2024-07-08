package top.aenlly.qqrobot.context;

import lombok.Builder;
import lombok.Data;
import net.mamoe.mirai.event.events.MessageEvent;
import top.aenlly.qqrobot.enmus.OptTypeEnum;

@Data
@Builder
public class CommandContext {

    /**
     * qq聊天内容，移除命令后
     */
    private String content;

    /**
     * 命令
     */
    private String command;

    /**
     * 命令类型
     */
    private OptTypeEnum optType;

    /**
     * 消息事件
     */
    private MessageEvent event;

    private Boolean openContinuedCommand;

    private boolean isAt;

}
