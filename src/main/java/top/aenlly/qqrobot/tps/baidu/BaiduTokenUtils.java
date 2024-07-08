package top.aenlly.qqrobot.tps.baidu;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.http.HttpHeaders;
import top.aenlly.qqrobot.enmus.MsgCode;
import top.aenlly.qqrobot.tps.baidu.properties.Baidu;
import top.aenlly.qqrobot.utils.ExceptionUtils;

import java.util.Date;
import java.util.HashMap;


/**
 * 获取百度的token类
 *
 * @author Aenlly
 * @create by date 2021/12/26 22:42
 * @projectName RefuseClassificationCultivate
 */
public class BaiduTokenUtils {

    private static BaiduAccessToken accessToken ;

    private static Date startDate;

    /**
     * 获取权限token
     *
     * @return 返回示例： { "access_token":
     *     "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567", "expires_in":
     *     2592000 }
     */
    public static String getToken(Baidu baidu) {
        if (startDate == null) {
            startDate = new Date();
        } else {
            // 获得当前时间
            Date date = new Date();
            // 获得当前时间的毫秒数
            long time = date.getTime();
            // 获得获取token时的时间的毫秒数
            long dateTime = startDate.getTime();
            // 计算时间差
            long l = (time - dateTime)/ DateUnit.SECOND.getMillis();
            // 计算天数
            // 判断天数
            if (l < accessToken.getExpiresIn()) {
                return accessToken.getAccessToken();
            }
        }
        startDate = new Date();
        accessToken = getAccessToken(baidu);
        return accessToken.getAccessToken();
    }

    /**
     * 获取API访问token 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param baidu - 百度云配置
     * @return assess_token 示例：
     *     "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static BaiduAccessToken getAccessToken(Baidu baidu) {
        // 获取token地址
        String getAccessTokenUrl = String.format(BaiduConstant.TOKEN_URL, baidu.getGrantType(), baidu.getClientId(), baidu.getClientSecret());
        HttpRequest post = HttpUtil.createPost(getAccessTokenUrl);
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, String.valueOf(HttpHeaderValues.APPLICATION_JSON));
        post.addHeaders(headers);
        String resultJson =post.execute().body();
        BaiduAccessToken result = JSONUtil.toBean(resultJson, BaiduAccessToken.class);
        if(StrUtil.isNotEmpty(result.getError())){
            ExceptionUtils.SystemException(MsgCode.BAIDU_TOKEN_ERROR,result.getErrorDescription());
        }
        return result;
    }

    /**
     * 会在url后面自动拼接access_token
     * @param o 参数
     * @param t 返回值
     * @param url
     * @return
     * @param <O>
     * @param <T>
     */
    public static <O,T> T post(O o,Class<T> t,String url){
        String resultJson = HttpUtil.post(url, JSONUtil.toJsonStr(o));
        return JSONUtil.toBean(resultJson,t);
    }
}
