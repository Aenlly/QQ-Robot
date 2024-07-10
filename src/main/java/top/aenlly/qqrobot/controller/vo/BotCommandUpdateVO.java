package top.aenlly.qqrobot.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BotCommandUpdateVO extends BotCommandAddVO {
    private long id;
}
