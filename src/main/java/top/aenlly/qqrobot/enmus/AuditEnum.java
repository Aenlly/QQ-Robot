package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditEnum {
    NONE("不做任何操作"),
    AUTO("自动审核"),
    NOTICE("通知")
    ;

    @EnumValue
    private final String value;
}
