package top.aenlly.qqrobot.tps.baidu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AIReqVO {
    private List<Message> messages=new ArrayList<>();

    @Data
    public static class  Message{
        private String role;

        private String content;
    }
}
