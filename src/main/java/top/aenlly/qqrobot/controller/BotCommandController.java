package top.aenlly.qqrobot.controller;

import org.springframework.web.bind.annotation.RestController;
import top.aenlly.qqrobot.controller.vo.BotCommandAddVO;
import top.aenlly.qqrobot.core.common.NoBody;
import top.aenlly.qqrobot.core.common.Result;


@RestController
public class BotCommandController {


    public Result<NoBody> add(BotCommandAddVO entity){
        return Result.buildSuccess();
    }
}
