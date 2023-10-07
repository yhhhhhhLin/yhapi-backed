package xyz.linyh.yhapigateway.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import xyz.linyh.yhapi.ducommon.model.entity.Interfaceinfo;
import xyz.linyh.yhapi.ducommon.service.DubboInterfaceinfoService;
import xyz.linyh.yhapigateway.service.RouteService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来对数据库里面的路由进行操作
 * @author lin
 */
@Component
@Slf4j
public class RouteServiceImpl implements RouteService {

    @DubboReference
    private DubboInterfaceinfoService dubboInterfaceinfoService;

    /**
     * 本地缓存，获取到的路由信息会保存到这个hashmap中
     * key为路由id（对应接口id）value为路由的详细
     */
    private final Map<String, Interfaceinfo> routes = new ConcurrentHashMap<>(256);



    /**
     * 获取数据库里面的所有路由信息
     */
    @Override
    public Map<String,Interfaceinfo> getRouteDefinitions() {
        if(routes.size()>0){
            return routes;

        }
        log.info("开始从数据库初始化interface路由信息");
        List<Interfaceinfo> interfaceinfos = dubboInterfaceinfoService.getAllInterface();
//        将路由信息保存到hashmap中
        for (Interfaceinfo interfaceinfo : interfaceinfos) {
            routes.put(String.valueOf(interfaceinfo.getUri()),interfaceinfo);
        }
        return routes;
    }


    @Override
    public Map<String,Interfaceinfo> getRoutes(){
        return routes;
    }

    /**
     * 新增新的路由
     *
     * @param serverRequest
     * @return
     */
    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return null;
    }

    /**
     * 查询所有路由
     *
     * @param serverRequest
     * @return
     */
    @Override
    public Mono<ServerResponse> queryAll(ServerRequest serverRequest) {
        return null;
    }

    /**
     * 查询某个路由
     *
     * @param serverRequest
     * @return
     */
    @Override
    public Mono<ServerResponse> queryOne(ServerRequest serverRequest) {
        return null;
    }

    /**
     * 删除某个路由
     *
     * @param serverRequest
     * @return
     */
    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return null;
    }
}
