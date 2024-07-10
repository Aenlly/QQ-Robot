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
    COMMAND_ADD("添加"),
    COMMAND_DEL("删除"),
    ERNIE_SPEED_128K("AI"),
    MUTE("禁言"),
    ANNOUNCEMENT("群公告"),
    ROLE("权限")

    ;

    private final String msg;
}
