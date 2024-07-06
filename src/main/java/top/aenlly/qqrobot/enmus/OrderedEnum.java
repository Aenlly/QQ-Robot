package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.Ordered;

@Getter
@AllArgsConstructor
public enum OrderedEnum implements Ordered {
    MIN(Integer.MIN_VALUE),
    DEFAULT(0),
    COMMAND(10),
    ORDINARY(15),
    MAX(Integer.MAX_VALUE)
    ;

    private final int order;
}
