package xyz.linyh.yhapi.service;


import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lin
* @description 针对表【userinterfaceinfo(用户接口调用次数关系表)】的数据库操作Service
* @createDate 2023-09-11 21:20:10
*/
public interface UserinterfaceinfoService extends IService<UserInterfaceinfo> {

    void validInterfaceInfo(UserInterfaceinfo userInterfaceinfo, boolean add);

    public BaseResponse invokeOk(Long interfaceInfoId, Long userId);

}
