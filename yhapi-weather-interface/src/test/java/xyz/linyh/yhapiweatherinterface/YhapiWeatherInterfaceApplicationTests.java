package xyz.linyh.yhapiweatherinterface;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class YhapiWeatherInterfaceApplicationTests {

    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "1010100");
        String s = HttpUtil.get("http://localhost:8085/weather/analyze", map);
    }

}
