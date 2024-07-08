package top.aenlly.qqrobot.tps.baidu;

import lombok.Data;

@Data
public class BaiduAccessToken {

    private String accessToken;

    private Integer expiresIn;

    private String refreshToken;

    private String scope;

    private String  sessionKey;

    private String sessionSecret;
    private String error;
    private String errorDescription;
}
