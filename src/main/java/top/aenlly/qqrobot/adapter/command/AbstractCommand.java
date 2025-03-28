package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.lang.Opt;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.MessageSourceKind;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.context.CommandContext;
import top.aenlly.qqrobot.context.GeneralContext;
import top.aenlly.qqrobot.core.common.CommandModeContext;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.enmus.MsgCode;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.time.LocalDateTime;

/**
 * 命令，根据需要实现
 */
@Slf4j
public abstract class AbstractCommand implements Command {

    protected MessageEvent event;

    protected CommandContext context;

    /**
     * 开启持续命令模式
     */
    protected boolean openContinuedCommand = false;

    @Override
    public void execute(CommandContext context) {
        this.context = context;
        event = context.getEvent();
        if (getOpenContinuedCommand() && !Opt.ofNullable(context.getOpenContinuedCommand()).orElse(false)) {
            openContinuedCommand();
            return;
        }

        if (isContinuedCommand()) {
            closeContinuedCommand();
            return;
        }

        if (event instanceof GroupMessageEvent) {
            execute((GroupMessageEvent) event);
        }
        if (event instanceof FriendMessageEvent) {
            execute((FriendMessageEvent) event);
        }
        if (event instanceof GroupTempMessageEvent) {
            execute((GroupTempMessageEvent) event);
        }
        if (event instanceof StrangerMessageEvent) {
            execute((StrangerMessageEvent) event);
        }
    }

    /**
     * 群消息事件
     *
     * @param event
     */
    protected void execute(GroupMessageEvent event) {}


    /**
     * 好友消息事件
     *
     * @param event
     */
    protected void execute(FriendMessageEvent event) {}


    /**
     * 群临时会话事件
     *
     * @param event
     */
    protected void execute(GroupTempMessageEvent event) {}

    /**
     * 陌生人消息事件
     * @param event
     */
    protected void execute(StrangerMessageEvent event) {}


    /**
     * 打开持续命令
     */
    private void openContinuedCommand() {
        MessageSourceKind kind = event.getSource().getKind();
        String id ;
        if (kind == MessageSourceKind.GROUP) {
            id = String.valueOf(((GroupMessageEvent) event).getGroup().getId())+event.getSender().getId();
        }
        else if (kind == MessageSourceKind.FRIEND) {
            id = String.valueOf(((FriendMessageEvent) event).getFriend().getId());
        }else {
            return;
        }
        GeneralContext.setCommand(kind.name() + id
                , CommandModeContext.builder()
                        .commandEnum(CommandEnum.valueOf(getName()))
                        .duration(30)
                        .startTime(LocalDateTime.now())
                        .build());
        log.info("开启命令：{}", getName());
        subjectMsg("已启用");
    }

    /**
     * 关闭继续命令
     *
     * @return
     */
    protected boolean isContinuedCommand() {
        return false;
    }

    /**
     * 关闭持续命令
     *
     * @return
     */
    protected void closeContinuedCommand() {
        MessageSourceKind kind = event.getSource().getKind();
        long id ;
        if (kind == MessageSourceKind.GROUP) {
            id = ((GroupMessageEvent) event).getGroup().getId();
        }
        else if (kind == MessageSourceKind.FRIEND) {
            id = ((FriendMessageEvent) event).getFriend().getId();
        }else {
            return;
        }
        GeneralContext.removeCommand(kind.name() + id);
        log.info("关闭命令：{}", getName());
        subjectMsg("已关闭");
    }

    /**
     * 发送消息
     * @param prefix
     */
    private void subjectMsg(String prefix) {
        send(prefix + "【" + CommandEnum.valueOf(context.getCommand()).getMsg() + "】模式");
    }

    /**
     * 是否开启持续模式
     * @return
     */
    public boolean getOpenContinuedCommand() {
        return openContinuedCommand;
    }

    /**
     * 权限不足报错
     */
    protected void sendPermission() {
        send(MsgCode.PERMISSIONS_MISSING.getMsg());
    }

    /**
     * 发送消息
     * @param message
     */
    protected void send(String message) {
        MessageUtils.senderQuoteReplyMessage(event, message);
    }

}
