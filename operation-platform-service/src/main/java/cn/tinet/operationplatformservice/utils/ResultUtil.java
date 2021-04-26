package cn.tinet.operationplatformservice.utils;


import cn.tinet.operationplatformservice.vo.ResponseDTO;
import cn.tinet.operationplatformservice.vo.ResponseEnum;

/**
 * @Time : 2021/3/3 21:55
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ResultUtil.java
 * @Software: IntelliJ IDEA
 **/
public class ResultUtil {

    public static ResponseDTO success(Object data){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseEnum.SUCCESS.getCode());
        responseDTO.setDescription(ResponseEnum.SUCCESS.getDescription());
        responseDTO.setData(data);
        return responseDTO;
    }

    public static ResponseDTO success(){
        return success(null);
    }

    public static ResponseDTO error(ResponseEnum responseEnum){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(responseEnum.getCode());
        responseDTO.setDescription(responseEnum.getDescription());
        return responseDTO;
    }
}
