package top.aenlly.qqrobot.adapter.command;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.enmus.MsgCode;
import top.aenlly.qqrobot.utils.ExceptionUtils;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.List;

@Slf4j
public abstract class AbstractCommand<T extends MessageEvent> implements Command {

    protected List<PlainText> msgTextList;

    protected T event;

    @Override
    public void execute(MessageEvent event) {
        before();
        init(event);
        after();
    }

    private void init(MessageEvent event){
        msgTextList = MessageUtils.getCommandPlainText(event);
        try {
            this.event = (T) event;
        } catch (Exception e) {
            ExceptionUtils.SystemException(MsgCode.CONVERTED, e.fillInStackTrace());
            log.error("event converted error");
        }
    }

    /**
     * 基础方法执行前
     */
    protected void before(){

    };
    /**
     * 基础方法执行后
     */
    protected abstract void after();
}
