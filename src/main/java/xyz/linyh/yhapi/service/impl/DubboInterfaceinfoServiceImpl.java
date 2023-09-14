package xyz.linyh.yhapi.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.service.DubboInterfaceinfoService;
import xyz.linyh.yhapi.service.InterfaceinfoService;

@DubboService
public class DubboInterfaceinfoServiceImpl implements DubboInterfaceinfoService {

    @Autowired
    private InterfaceinfoService interfaceinfoService;

    @Override
    public Boolean getInterfaceByURL(String interfaceURL,String method) {
        return interfaceinfoService.getInterfaceInfoByURL(interfaceURL,method);
    }
}
