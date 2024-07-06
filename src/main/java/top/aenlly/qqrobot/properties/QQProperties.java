package top.aenlly.qqrobot.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Aenlly||tnw
 * @create 2024/06/06 17:27
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "qq")
@Data
public class QQProperties {

    private List<Long> admin;

    private Account account;

    @Data
    @NoArgsConstructor
    public static class Account{

        private Long qq;

        private String password;
    }
}
