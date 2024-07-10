package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleTypeEnum {

    NONE("NONE","无任何权限"),
    ADD("ADD","添加权限"),
    UPDATE("UPDATE","修改权限"),
    DELETE("DELETE","删除权限"),
    ALL("ALL","所有权限"),
    QUERY("QUERY","查询权限"),
    MUTE("MUTE","禁言权限"),
    T("T","踢人权限"),
    ANNOUNCEMENT("ANNOUNCEMENT","群公告操作权限"),
    ;

    @EnumValue
    private final String value;

    private final String label;
}
