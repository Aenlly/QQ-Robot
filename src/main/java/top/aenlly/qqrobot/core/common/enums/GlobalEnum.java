package top.aenlly.qqrobot.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.aenlly.qqrobot.core.ResultCapable;

/**
 * @author Aenlly||tnw
 * @create 2024/07/05 14:35
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum GlobalEnum implements ResultCapable {

    SUCCESS(200, "success"),
    SYSTEM_ERROR(999, "system error"),
    ;

    private int code;

    private String msg;

}
