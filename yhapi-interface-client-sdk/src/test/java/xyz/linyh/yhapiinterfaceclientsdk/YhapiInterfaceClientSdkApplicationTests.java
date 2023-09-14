package xyz.linyh.yhapiinterfaceclientsdk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yhapiinterfaceclientsdk.client.TestClient;

@SpringBootTest
class YhapiInterfaceClientSdkApplicationTests {

    @Test
    void contextLoads() {
        TestClient testClient = new TestClient();
        System.out.println(testClient.testGet("lin"));
    }

}
