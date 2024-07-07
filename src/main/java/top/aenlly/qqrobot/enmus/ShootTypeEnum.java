package top.aenlly.qqrobot.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShootTypeEnum {

    POSITIVE("正片"),
    FIELD_PHOTO("场照"),
    ;

    @EnumValue
    private final String value;
}
