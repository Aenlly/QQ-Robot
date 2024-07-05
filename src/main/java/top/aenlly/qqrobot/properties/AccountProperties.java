package top.aenlly.qqrobot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:27
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "qq.account")
@Data
public class AccountProperties {
    private Long qq;

    private String password;
}
