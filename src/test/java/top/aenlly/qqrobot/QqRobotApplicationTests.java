package top.aenlly.qqrobot;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import top.aenlly.qqrobot.constant.SiteConstant;
import top.aenlly.qqrobot.tps.pixiv.MessageVO;
import top.aenlly.qqrobot.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

class QqRobotApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] strings) {
        HashMap<String, String> header = new HashMap<>();
        String url = String.format(SiteConstant.PIXIV, DateUtil.formatDate(DateUtil.date().offset(DateField.HOUR,-25)), 10000);
        MessageVO messageVO = HttpUtils.get(url, header, MessageVO.class);
        List<MessageVO.MessageData> messageData = messageVO.getData();
        // 随机获取一张原图地址
        MessageVO.MessageData data = messageData.get(new Random().nextInt(0, messageData.size()));
        List<MessageVO.ImageUrl> imageUrls = data.getImageUrls();
        String original = imageUrls.get(new Random().nextInt(0, imageUrls.size())).getOriginal();

        header.put(HttpHeaders.REFERER, SiteConstant.PIXIV_REFERER);
        String replace = original.replace("https://i.pximg.net", "https://o.edcms.pw");
        String[] urls = replace.split("/");
        Boolean downloadFile = HttpUtils.getDownloadFile(replace, header, urls[urls.length-1]);
        if (downloadFile){
        }
    }
}
