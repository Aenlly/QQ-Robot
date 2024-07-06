package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OptTypeEnum {

    /**
     * 普通(回复消息型)
     */
    ORDINARY("ORDINARY"),
    /**
     * 命令
     */
    COMMAND("COMMAND"),
    ;

    @EnumValue
    private final String value;
}
