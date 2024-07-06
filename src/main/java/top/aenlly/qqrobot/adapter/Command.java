package top.aenlly.qqrobot.adapter;

import net.mamoe.mirai.event.events.MessageEvent;

public interface Command {

    /**
     * 命令
     * @return 命令
     */
    String getName();

    /**
     * 执行
     * @param event 消息事件
     */
    void execute(MessageEvent event);
}
