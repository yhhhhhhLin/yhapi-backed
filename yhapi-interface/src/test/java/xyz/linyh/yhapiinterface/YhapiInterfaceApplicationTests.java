package xyz.linyh.yhapiinterface;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yhapiinterface.client.TestClient;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest(classes = YhapiInterfaceApplicationTests.class)
class YhapiInterfaceApplicationTests {


//    这样子每一个调用的都可以无限调用不用验证身份
    @Test
    void contextLoads() {
        TestClient testClient = new TestClient();
        System.out.println(testClient.testGet("tom"));
    }

//    添加accesskey和secerykey这两个用来验证身份
    @Test
    void test2(){

        String accessKey = "testak";
        String secretKey = "testsk";
        TestClient testClient = new TestClient(accessKey,secretKey);
        System.out.println(testClient.testGet("tom"));
    }

//    利用api签名认证
    @Test
    void test3(){
        String accessKey = "testak";
        String secretKey = "testsk";
        String body = "zhang";
        TestClient testClient = new TestClient(accessKey,secretKey);

        System.out.println(testClient.testGet(body));


    }

//    @Test
//    public void test4(){
//        System.out.println(testClient.testGet("lin"));
//    }

    @Test
    public void timet(){
        Timestamp timestamp = DateUtil.offset(DateUtil.date(), DateField.MINUTE, 5).toTimestamp();
        Timestamp timestamp1 = DateUtil.date().toTimestamp();
        long time = timestamp.getTime();
        long time1 = timestamp1.getTime();
        long l = time - time1;
        System.out.println(timestamp1);
    }
}
