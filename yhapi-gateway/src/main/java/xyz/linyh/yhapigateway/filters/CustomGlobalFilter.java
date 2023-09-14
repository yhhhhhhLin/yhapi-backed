package xyz.linyh.yhapigateway.filters;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.linyh.yhapiinterfaceclientsdk.utils.MyDigestUtils;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse response = exchange.getResponse();

//        3. 统一日志
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getPath());
        log.info("请求地址:{}",request.getPath());
        log.info("请求参数:{}",request.getQueryParams());
        log.info("请求方法:{}",request.getMethod());
        log.info("请求用户地址:{}",request.getRemoteAddress());
        log.info("请求体:{}",request.getBody());

//        4. todo 设置请求黑白名单

//        5. 统一鉴权
        HttpHeaders headers = request.getHeaders();
        String sign = headers.getFirst("sign");
        String timeS = headers.getFirst("timeS");
        String accessKey = headers.getFirst("accessKey");
        String randomNum = headers.getFirst("randomNum");
        if(StrUtil.hasBlank(sign,timeS,accessKey,randomNum)){
            log.info("缺少认证参数");
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        try {
//            api签名认证
            signAuth(sign,timeS,accessKey);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            log.info("签名认证失败");
            return response.setComplete();
        }
//        6. 判断请求接口是否存在
//        todo 根据url获取去数据库判断接口是否存在 可以直接用远程调用backed项目的接口

//        7. 转发到对应的接口
        Mono<Void> filter = chain.filter(exchange);
        return handleResponse(exchange,chain);
//        这个是异步的方法，需要全部过滤都结束才会转发到对应的服务上 所以无法通过filter来获取响应结果

//        return filter;
    }


    private static Joiner joiner = Joiner.on("");
    /**
     * 处理异步发送请求无法获取响应值
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 保存数据的工厂
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 增强响应对象
        ServerHttpResponseDecorator response = new ServerHttpResponseDecorator(originalResponse) {
            // 响应调用完才会执行
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                    // 获取ContentType，判断是否返回JSON格式数据
                    String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    if (StrUtil.isNotBlank(originalResponseContentType) ) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        //（返回数据内如果字符串过大，默认会切割）解决返回体分段传输
                        // 往返回值里面写数据
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            List<String> list = new ArrayList<>();
                            dataBuffers.forEach(dataBuffer -> {
                                try {
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    DataBufferUtils.release(dataBuffer);
                                    list.add(new String(content, "utf-8"));
                                } catch (Exception e) {
                                    log.info("加强response出现错误------------");
                                }
                            });
                            String responseData = joiner.join(list);
//                           todo 请求成功 调用次数修改 (利用远程调用修改)
//                            8. todo 响应日志

//                            9. todo 次数统计
                            System.out.println("responseData："+responseData);


                            byte[] uppedContent = new String(responseData.getBytes(), Charset.forName("UTF-8")).getBytes();
                            originalResponse.getHeaders().setContentLength(uppedContent.length);
                            return bufferFactory.wrap(uppedContent);
                        }));
                    }
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
        return chain.filter(exchange.mutate().response(response).build());
    }

    /**
     * api签名认证
     * @param sign
     * @param timeS
     * @param ak
     * @return
     */
    Boolean signAuth(String sign,String timeS,String ak){
//        todo 从根据accessKey从数据库获取secretKet 可以直接用远程调用backed项目的接口
        String secretKey = "testsk";
//        通过加密算法加密生成然后和sign比较
//        认证生成签名
        if(!sign.equals(MyDigestUtils.getDigest(secretKey))){
            throw new RuntimeException("签名认证不通过");
        }

//        判断时间是否超出 获取5分钟后的时间戳
        Long nowTime = DateUtil.date().toTimestamp().getTime()/1000;
        Long time = Long.valueOf(timeS);
        if((nowTime-time)>5*30){
            throw new RuntimeException("超出时间");
        }
        return true;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}