package top.aenlly.qqrobot.core.common;

import lombok.Builder;
import lombok.Data;
import top.aenlly.qqrobot.enmus.CommandEnum;

import java.time.LocalDateTime;

@Data
@Builder
public class CommandModeContext {

    private CommandEnum commandEnum;

    private LocalDateTime startTime;

    /**
     * 持续时间
     */
    private int duration;

}
