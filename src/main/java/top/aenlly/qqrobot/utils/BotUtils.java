package top.aenlly.qqrobot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import top.aenlly.qqrobot.entity.SysBotEntity;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotUtils {

    /**
     * 创建默认的机器人
     * @param sysBotDO
     * @return
     */
    @SneakyThrows
    public static Bot defaultBot(SysBotEntity sysBotDO) {
        FixProtocolVersion.load(BotConfiguration.MiraiProtocol.ANDROID_PAD, ResourceUtils.getFile("classpath:config/9.0.56.json"));

        Bot bot = BotFactory.INSTANCE.newBot(sysBotDO.getQq(), sysBotDO.getPassword());
        BotConfiguration configuration = bot.getConfiguration();
        configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
        File file = ResourceUtils.getFile("classpath:config/deviceInfo.json");
        String deviceInfo = FileUtils.readFileToString(file, "UTF-8");
        configuration.loadDeviceInfoJson(deviceInfo);
        return bot;
    }
}
