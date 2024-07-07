package top.aenlly.qqrobot;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class QqRobotApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] strings) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");

        String result= HttpUtil.post("https://www.baidu.com", paramMap);
        System.out.println();
    }
}
