package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgCode implements AlertCapable {

    PARAMS_ERROR_1("SYS500001","参数错误：%s，应为：%s"),
    PARAMS_ERROR_2("SYS500002","参数错误"),
    PARAMS_ERROR_3("SYS500003","操作失败"),
    OPT_SUCCESS("SYS000001","操作成功"),
    CONVERTED("SYS900001","转换错误"),
    BAIDU_TOKEN_ERROR("SYS900002","获取token失败"),
    PERMISSIONS_MISSING("SYS000403","权限不足"),
    ;

    private final String code;
    private final String msg;
}
