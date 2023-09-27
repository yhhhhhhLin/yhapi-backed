package xyz.linyh.yhapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yapiclientsdk.client.ApiClient;
import xyz.linyh.yhapi.MyApplication;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MyApplication.class)
class AnalyzeControllerTest {


//    @Test
//    void analyzeInterfaceInfo() {
//        AnalyzeController analyzeController = new AnalyzeController();
//        System.out.println(analyzeController.analyzeInterfaceInfo());
//    }

    @Autowired
    private ApiClient apiClient;

    @Test
    void sdkTest() {
        String request = apiClient.request("/get/get2");
        System.out.println(request);
    }
}