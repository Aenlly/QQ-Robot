package top.aenlly.qqrobot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import top.aenlly.qqrobot.entity.SysBotEntity;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotUtils.class);

    /**
     * 创建默认的机器人
     * @param sysBotDO
     * @return
     */
    @SneakyThrows
    public static Bot defaultBot(SysBotEntity sysBotDO) {
        String dir = System.getProperty("user.dir");
        File version = ResourceUtils.getFile(dir+"/config/9.0.56.json");
        LOGGER.info(version.getAbsolutePath());
        FixProtocolVersion.load(BotConfiguration.MiraiProtocol.ANDROID_PAD, version);
        Bot bot = BotFactory.INSTANCE.newBot(sysBotDO.getQq(), sysBotDO.getPassword());
        BotConfiguration configuration = bot.getConfiguration();
        configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
        File file = ResourceUtils.getFile(dir+"/config/deviceInfo.json");
        LOGGER.info(version.getAbsolutePath());
        String deviceInfo = FileUtils.readFileToString(file, "UTF-8");
        configuration.loadDeviceInfoJson(deviceInfo);
        return bot;
    }
}
