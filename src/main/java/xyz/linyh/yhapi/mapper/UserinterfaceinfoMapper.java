package xyz.linyh.yhapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lin
* @description 针对表【userinterfaceinfo(用户接口调用次数关系表)】的数据库操作Mapper
* @createDate 2023-09-11 21:20:10
* @Entity xyz.linyh.yhapi.model.entity.UserInterfaceinfo
*/
@Mapper
public interface UserinterfaceinfoMapper extends BaseMapper<UserInterfaceinfo> {

}




