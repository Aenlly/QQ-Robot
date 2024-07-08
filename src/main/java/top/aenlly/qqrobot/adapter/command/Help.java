package top.aenlly.qqrobot.adapter.command;

import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.enmus.CommandEnum;

@Component
public class Help extends AbstractCommand {
    @Override
    public String getName() {
        return CommandEnum.HELP.name();
    }


}
