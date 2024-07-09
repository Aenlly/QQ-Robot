package top.aenlly.qqrobot.adapter.command;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.constant.SiteConstant;
import top.aenlly.qqrobot.enmus.CommandEnum;
import top.aenlly.qqrobot.tps.pixiv.MessageVO;
import top.aenlly.qqrobot.utils.HttpUtils;
import top.aenlly.qqrobot.utils.MessageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class Pixiv extends AbstractCommand{

    private static Random RANDOM = new Random();
    private static int PAGE_SIZE =10000;
    @Override
    public String getName() {
        return CommandEnum.PIXIV.name();
    }

    @Override
    protected void execute(GroupMessageEvent event) {
        HashMap<String, String> header = new HashMap<>();
        String url = String.format(SiteConstant.PIXIV, DateUtil.formatDate(DateUtil.date().offset(DateField.HOUR,-25)), PAGE_SIZE);
        MessageVO messageVO = HttpUtils.get(url, header, MessageVO.class);
        List<MessageVO.MessageData> messageData = messageVO.getData();
        // 随机获取一张原图地址
        MessageVO.MessageData data = messageData.get(RANDOM.nextInt(0, messageData.size()));
        List<MessageVO.ImageUrl> imageUrls = data.getImageUrls();
        String original = imageUrls.get(RANDOM.nextInt(0, imageUrls.size())).getOriginal();

        header.put(HttpHeaders.REFERER, SiteConstant.PIXIV_REFERER);
        String replace = original.replace(SiteConstant.PXIMG_NET, SiteConstant.EDCMS_PW);
        String[] urls = replace.split("/");
        String fileName = urls[urls.length - 1];
        Boolean downloadFile = HttpUtils.getDownloadFile(replace, header, fileName);
        if (downloadFile){
            MessageUtils.senderImage(event, fileName);
        }
    }
}
