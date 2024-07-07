package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgCode implements AlertCapable {

    CONVERTED("SYS300000","转换错误"),
    PARAMS_ERROR_1("SYS500000","参数错误：%s，应为：%s"),
    PARAMS_ERROR_2("SYS500000","参数错误"),
    OPT_SUCCESS("SYS000001","操作成功")
    ;

    private final String code;
    private final String msg;
}
