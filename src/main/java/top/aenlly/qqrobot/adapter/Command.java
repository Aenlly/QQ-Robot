package top.aenlly.qqrobot.adapter;

import top.aenlly.qqrobot.context.CommandContext;

public interface Command {

    /**
     * 命令
     * @return 命令
     */
    String getName();

    /**
     * 执行
     * @param context 上下文
     */
    void execute(CommandContext context);
}
