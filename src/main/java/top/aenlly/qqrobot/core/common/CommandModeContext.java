package top.aenlly.qqrobot.core.common;

import lombok.Data;
import top.aenlly.qqrobot.enmus.CommandEnum;

import java.time.LocalDateTime;

@Data
public class CommandModeContext {

    private CommandEnum commandEnum;



    private LocalDateTime startTime;

    /**
     * 持续时间
     */
    private Long duration;

}
