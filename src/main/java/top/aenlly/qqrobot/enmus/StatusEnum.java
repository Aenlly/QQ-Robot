package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 禁用
     */
    DISABLE("DISABLE"),
    /**
     * 启用
     */
    ENABLED("ENABLED"),
    ;

    @EnumValue
    private final String value;
}
