package top.aenlly.qqrobot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.aenlly.qqrobot.core.common.MessageTemplate;
import top.aenlly.qqrobot.enmus.AlertCapable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {

    public static void SystemException(AlertCapable alertCapable,Throwable throwable){
        throw new RuntimeException(String.format(MessageTemplate.ERROR_MSG.getValue(),alertCapable.getCode(),alertCapable.getMsg()),throwable);
    }

    public static void SystemException(AlertCapable alertCapable,String msg){
        throw new RuntimeException(String.format(MessageTemplate.ERROR_MSG.getValue(),alertCapable.getCode(),alertCapable.getMsg())+"\n"+msg);
    }
}
