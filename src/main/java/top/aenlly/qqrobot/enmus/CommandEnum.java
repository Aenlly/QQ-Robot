package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {
    DEFAULT("默认"),
    GET("获取"),
    ADD("添加"),
    DELETE("删除"),
    UPDATE("修改"),
    HELP("帮助"),
    T("踢出"),
    SHOOT_FINAL_MONEY("尾款"),
    PIXIV("随机pixiv图片"),
    EXIT("退出"),
    GROUP_ADD("群添加"),
    ERNIE_SPEED_128K("AI")

    ;

    private final String msg;
}
