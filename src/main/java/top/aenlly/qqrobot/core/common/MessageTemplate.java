package top.aenlly.qqrobot.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageTemplate {

    GROUP_AUDIT_MESSAGE_NOTICE("群：%s\n申请人：%s\n有一条新的入群申请，请尽快处理！"),
    GROUP_BLACK_NOTICE("群：%s\n申请人：%s\n属于群黑名单,需要移除黑名单后才可添加！"),
    SHOOT_ORDER_FINAL("用户:%s\n总金额:%d\n定金:%d\n待付尾款：%d\n待拍摄时间段：\n%s"),
    KICK("原因未知，请联系管理员说明！"),
    ERROR_MSG("error code :%s ,msg info: %s")

    ;

    private final String value;
}
