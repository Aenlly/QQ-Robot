package top.aenlly.qqrobot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.aenlly.qqrobot.core.common.Result;
import top.aenlly.qqrobot.service.BotService;

import java.util.List;

/**
 * @author Aenlly||tnw
 * @create 2024/07/05 14:29
 * @since 1.0.0
 */

@RestController
@RequestMapping("/v1/bot")
public class BotController {

    @Autowired
    private BotService botService;


    public Result<List<Long>> getBotList() {
        return Result.buildSuccess(botService.getBotList());
    }
}
