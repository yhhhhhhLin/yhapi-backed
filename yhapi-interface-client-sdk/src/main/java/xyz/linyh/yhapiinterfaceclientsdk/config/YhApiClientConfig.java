package xyz.linyh.yhapiinterfaceclientsdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.linyh.yhapiinterfaceclientsdk.client.TestClient;

@Configuration
//到时候配置文件yml就会有这个前缀对应的ak和sk提示
@ConfigurationProperties("yhapi.client")
@Data
@ComponentScan
public class YhApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public TestClient testClient(){
        return new TestClient(accessKey,secretKey);
    }
}
