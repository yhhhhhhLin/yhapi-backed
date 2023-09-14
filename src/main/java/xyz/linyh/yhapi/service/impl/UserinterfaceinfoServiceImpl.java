package xyz.linyh.yhapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.common.ErrorCode;
import xyz.linyh.yhapi.ducommon.common.ResultUtils;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;

import xyz.linyh.yhapi.exception.BusinessException;
import xyz.linyh.yhapi.mapper.UserinterfaceinfoMapper;
import xyz.linyh.yhapi.service.UserinterfaceinfoService;


/**
* @author lin
* @description 针对表【userinterfaceinfo(用户接口调用次数关系表)】的数据库操作Service实现
* @createDate 2023-09-11 21:20:10
*/
@DubboService
public class UserinterfaceinfoServiceImpl extends ServiceImpl<UserinterfaceinfoMapper, UserInterfaceinfo>
    implements UserinterfaceinfoService {



    /**
     * 对接口信息进行校验
     *
     * @param userInterfaceinfo
     * @param add
     */
    @Override
    public void validInterfaceInfo(UserInterfaceinfo userInterfaceinfo, boolean add) {
        if (userInterfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = userInterfaceinfo.getId();
        Long userId = userInterfaceinfo.getUserId();
        Integer remNum = userInterfaceinfo.getRemNum();
        Integer status = userInterfaceinfo.getStatus();
        Integer allNum = userInterfaceinfo.getAllNum();


        // 创建时，所有参数必须非空
        if (add) {
            if (userId <=0 || remNum<0 || allNum<0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (id==null || id<=0 || userId <=0 || remNum<0 || allNum<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
//        if ((status!=0 && status!=1)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }

    }

//    todo 调用成功，调用次数+1，可调用次数-1

    /**
     * 调用成功 调用次数+1，可调用次数-1
     * @param interfaceInfoId
     * @return
     */
    @Override
    public BaseResponse invokeOk(Long interfaceInfoId, Long userId){
        if(interfaceInfoId==null || userId==null ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"确实接口id或用户id");
        }
//        调用次数+1，可调用次数-1
        LambdaUpdateWrapper<UserInterfaceinfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInterfaceinfo::getUserId,userId)
                        .eq(UserInterfaceinfo::getInterfaceId,interfaceInfoId)
                                .setSql("remNum = remNum-1,allNum = allNum+1");
        boolean update = this.update(wrapper);
        return ResultUtils.success(update);
    }

}




