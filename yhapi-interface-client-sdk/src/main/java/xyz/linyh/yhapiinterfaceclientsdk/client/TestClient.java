package xyz.linyh.yhapiinterfaceclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.stereotype.Component;
import xyz.linyh.yhapiinterfaceclientsdk.utils.MyDigestUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestClient {

    private String accessKey;

    private String secretKey;

    public static final String GATEWAY_PRE_PATH = "http://localhost:8081";

    public TestClient() {
    }

    public TestClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

//    调用到api提供者的对应方法
    public String testGet(String name){
//        利用签名认证(服务端也需要修改)
        //        1. 将请求体和ak利用加密算法加密
//        生成签名
        String sign = MyDigestUtils.getDigest(secretKey);
//        获取随机数,这个需要去数据库里面获取(随机数会定时刷新)
        String randomNumbers = RandomUtil.randomNumbers(3);
//        时间(可以用来判断请求是否超出时间有效)
        String timeS = String.valueOf(System.currentTimeMillis()/1000);
//        发送请求到接口真实地址
        HttpResponse response = HttpRequest.get(GATEWAY_PRE_PATH+"/api/g" + "?name=" + name)
                .addHeaders(getHeader(sign,timeS)).execute();
        System.out.println(response);
        if(response!=null){
            return response.body();
        }
        return null;
    }

    public String testPost(String name){
//        利用签名认证(服务端也需要修改)
        //        1. 将请求体和ak利用加密算法加密
//        生成签名
        String sign = MyDigestUtils.getDigest(secretKey);
//        获取随机数,这个需要去数据库里面获取(随机数会定时刷新)
        String randomNumbers = RandomUtil.randomNumbers(3);
//        时间(可以用来判断请求是否超出时间有效)
        String timeS = String.valueOf(System.currentTimeMillis());
//        发送请求到接口真实地址
        HttpResponse response = HttpRequest.get(GATEWAY_PRE_PATH+"/api/g" + "?name=" + name)
                .addHeaders(getHeader(sign,timeS)).execute();
        System.out.println(response);
        if(response!=null){
            return response.body();
        }
        return null;
    }

    private Map getHeader(String sign,String timeS) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("accessKey",accessKey);
//        headers.put("secretKey",secretKey);
        headers.put("sign",sign);
        headers.put("timeS",String.valueOf(System.currentTimeMillis()/1000));
//        这个随机数要从数据库获取的
        headers.put("randomNum",RandomUtil.randomNumbers(2));
        headers.put("timeS",timeS);
        return headers;
    }


}
