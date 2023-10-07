package xyz.linyh.yhapiinterface;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yhapiinterface.client.TestClient;
import xyz.linyh.yhapiinterface.entitys.ThisUser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest(classes = YhapiInterfaceApplicationTests.class)
class YhapiInterfaceApplicationTests {


//    这样子每一个调用的都可以无限调用不用验证身份
    @Test
    void contextLoads() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "tom");
        String JSON = JSONUtil.toJsonStr(requestBody);
        System.out.println(JSON);
        HttpRequest request = HttpRequest.post("http://localhost:7601/api/p").body(JSON);
        request.execute();
    }


}
