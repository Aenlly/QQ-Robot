package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.constant.SiteConstant;
import top.aenlly.qqrobot.enmus.CommandEnum;

@Component
public class Pixiv extends AbstractCommand{
    private int pageSize=10000;
    @Override
    public String getName() {
        return CommandEnum.PIXIV.name();
    }


    @Override
    protected void execute(GroupMessageEvent event) {
        String result = HttpUtil.get(String.format(SiteConstant.PIXIV, DateUtil.formatDate(DateUtil.date()), pageSize));
    }
}
