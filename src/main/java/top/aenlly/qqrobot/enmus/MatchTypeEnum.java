package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchTypeEnum {
    /**
     * 精准匹配
     */
    EXACT("EXACT"),
    /**
     * 正则匹配
     */
    REGEX("REGEX"),
    /**
     * 前缀匹配
     */
    PREFIX("PREFIX")
    ;

    @EnumValue
    private final String value;

}
