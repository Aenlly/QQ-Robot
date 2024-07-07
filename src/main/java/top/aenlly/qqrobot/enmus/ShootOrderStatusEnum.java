package top.aenlly.qqrobot.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShootOrderStatusEnum {
    THE_DEPOSIT_HAS_BEEN_PAID("定金已付"),
    WAITING_FOR_THE_TAIL("尾款待付"),
    TO_BE_FILMED("待拍摄"),
    THE_FULL_AMOUNT_HAS_BEEN_PAID("全款已付"),
    TO_BE_RETURNED ("待返图"),
    END_OF_PROCESS("流程结束"),
    ;

    private final String value;
}
