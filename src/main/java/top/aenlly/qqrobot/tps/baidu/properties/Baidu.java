package top.aenlly.qqrobot.tps.baidu.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConditionalOnProperty(prefix = "aenlly.robot.baidu",value = "enable",havingValue = "true")
@ConfigurationProperties(prefix = "aenlly.robot.baidu")
public class Baidu {

    private Boolean enable = false;

    private String authType="accessToken";

    private String grantType="client_credentials";

    /**
     * clientId,官网获取的 API Key 更新为你注册的 百度云应用的AK
     */
    private String clientId;
    /**
     * 官网获取的 Secret Key 更新为你注册的 百度云应用的SK
     */
    private String clientSecret;
}
