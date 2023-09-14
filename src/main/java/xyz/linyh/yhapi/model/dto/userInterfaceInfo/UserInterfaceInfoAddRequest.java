package xyz.linyh.yhapi.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {



    /**
     * 用户id
     */
    private Long userId;

    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 剩余调用次数
     */
    private Integer remNum;



    /**
     * 用户是否能调用这个接口 0为可用 1为不可用
     */
    private Integer status;




    private static final long serialVersionUID = 1L;
}