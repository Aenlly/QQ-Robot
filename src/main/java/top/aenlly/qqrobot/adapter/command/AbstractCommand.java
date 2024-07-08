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
import top.aenlly.qqrobot.utils.MessageUtils;

import java.time.LocalDateTime;

@Slf4j
public abstract class AbstractCommand implements Command {

    protected MessageEvent event;

    protected CommandContext context;

    /**
     * 开启持续命令模式
     */
    protected boolean openContinuedCommand=false;

    @Override
    public void execute(CommandContext context) {
        this.context=context;
        event=context.getEvent();
        if(getOpenContinuedCommand()&&!Opt.ofNullable(context.getOpenContinuedCommand()).orElse(false)) {
            openContinuedCommand();
            return;
        }
        if(event instanceof GroupMessageEvent){
            execute((GroupMessageEvent) event);
        }
        if(event instanceof FriendMessageEvent){
            execute((FriendMessageEvent) event);
        }
        if(event instanceof GroupTempMessageEvent){
            execute((GroupTempMessageEvent) event);
        }
        if(event instanceof StrangerMessageEvent){
            execute((StrangerMessageEvent) event);
        }
    }

    protected void execute(GroupMessageEvent event){};
    protected void execute(FriendMessageEvent event){};
    protected void execute(GroupTempMessageEvent event){};
    protected void execute(StrangerMessageEvent event){};

    private void openContinuedCommand() {

        MessageSourceKind kind = event.getSource().getKind();
        GeneralContext.setCommand(kind.name()+event.getSender().getId()
                ,CommandModeContext.builder()
                        .commandEnum(CommandEnum.valueOf(getName()))
                        .duration(30)
                        .startTime(LocalDateTime.now())
                        .build());
        openSubjectMsg();
        log.info("开启命令：{}",getName());
    }

    protected void openSubjectMsg(){
        MessageUtils.senderQuoteReplyMessage(event,String.format("已进入:【%s】",CommandEnum.valueOf(context.getCommand()).getMsg()));
    }

    public boolean getOpenContinuedCommand() {
        return openContinuedCommand;
    }
}
