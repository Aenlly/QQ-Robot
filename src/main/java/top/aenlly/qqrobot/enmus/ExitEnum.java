package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExitEnum {

    EXIT("EXIT"),
    exit("exit"),
    chinese("退出")
    ;

    private final String value;

    public static ExitEnum[] values = values();
}
