package xyz.linyh.yhapiinterface.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestClient {

    private String accessKey;

    private String secretKey;

    public TestClient() {
    }

    public TestClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String testGet(String name){
//        最简单，没有身份证明
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", name);
//        String result= HttpUtil.get("http://localhost:7600/test_i/g",paramMap);
//        添加身份证明(但是这样子密钥在服务器之间传输，可能会被拦截)
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", name);
//        HttpResponse response = HttpRequest.get("http://localhost:7600/test_i/g" + "?name=" + name)
//                .header("accessKey",accessKey)
//                .header("secretKey",secretKey).execute();

//        利用签名认证(服务端也需要修改)
        //        1. 将请求体和ak利用加密算法加密
        Digester SHA256 = new Digester(DigestAlgorithm.SHA256);
//        生成签名
        String sign = SHA256.digestHex(name + secretKey);
//        获取随机数,这个需要去数据库里面获取(随机数会定时刷新)
        String randomNumbers = RandomUtil.randomNumbers(3);
//        时间(可以用来判断请求是否超出时间有效)
        String timeS = String.valueOf(System.currentTimeMillis());
//        发送请求到接口真实地址
        HttpResponse response = HttpRequest.get("http://localhost:7600/test_i/g" + "?name=" + name)
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

    public String testPost(String name){
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);

        String result= HttpUtil.post("http://localhost:7600/test_i/p", paramMap);
        return result;
    }
}
