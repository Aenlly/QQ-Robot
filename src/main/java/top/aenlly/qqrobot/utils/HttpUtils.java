package top.aenlly.qqrobot.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 会在url后面自动拼接access_token
     * @param t 参数
     * @param r 返回值
     * @param url
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T,R> R post(T t,Class<R> r,String url){
        String resultJson = HttpUtil.post(url, JSONUtil.toJsonStr(t));
        return JSONUtil.toBean(resultJson,r);
    }

    public static <T,R> R get(String url, Map<String,String> header,Class<R> r){
        HttpRequest req = HttpUtil.createGet(url);
        req.addHeaders(header);
        String body = req.execute().body();
        return JSONUtil.toBean(body, r);
    }

    public static Boolean getDownloadFile(String url,Map<String,String> header,String fileName){
        // get请求下载文件
        HttpRequest get = HttpUtil.createGet(url);
        get.setConnectionTimeout(1000*60);
        get.addHeaders(header);
        try(InputStream inputStream = get.execute().bodyStream();
            FileOutputStream outputStream = new FileOutputStream(fileName)) {
                byte[] buffer = new byte[8192]; // 使用8KB的缓冲区
                int n;
                while (-1 != (n = inputStream.read(buffer))) {
                    outputStream.write(buffer, 0, n);
                }
        }catch (IOException e){
            LOGGER.error("图片下载或保存失败：" + e.getMessage());
            return false;
        }
        return true;
    }
}
