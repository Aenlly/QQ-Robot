package top.aenlly.qqrobot.core.common;

import lombok.Data;
import top.aenlly.qqrobot.core.common.enums.GlobalEnum;
import top.aenlly.qqrobot.core.ResultCapable;

/**
 * @author Aenlly||tnw
 * @create 2024/07/05 14:31
 * @since 1.0.0
 */
@Data
public class Result<T> {

    private int code;

    private String msg;

    private T body;


    public static <T> Result<T> buildSuccess(int code, String msg, T body) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setBody(body);
        return result;
    }

    public static <T> Result<T> buildSuccess(T body) {
        Result<T> result = new Result<>();
        result.setCode(GlobalEnum.SUCCESS.getCode());
        result.setMsg(GlobalEnum.SUCCESS.getMsg());
        result.setBody(body);
        return result;
    }


    public static <T> Result<T> buildResult(ResultCapable resultCapable, T body) {
        Result<T> result = new Result<>();
        result.setCode(resultCapable.getCode());
        result.setMsg(resultCapable.getMsg());
        result.setBody(body);
        return result;
    }

    public static Result<NoBody> buildSuccess() {
        Result<NoBody> result = new Result<>();
        result.setCode(GlobalEnum.SUCCESS.getCode());
        result.setMsg(GlobalEnum.SUCCESS.getMsg());
        return result;
    }

    public static Result<NoBody> buildSystemError() {
        Result<NoBody> result = new Result<>();
        result.setCode(GlobalEnum.SYSTEM_ERROR.getCode());
        result.setMsg(GlobalEnum.SYSTEM_ERROR.getMsg());
        return result;
    }
}
