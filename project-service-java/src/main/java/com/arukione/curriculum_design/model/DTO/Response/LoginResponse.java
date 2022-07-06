package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.arukione.curriculum_design.model.DTO.Response.BaseInfoResponse;
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends BaseInfoResponse{
    String accessToken;
    public LoginResponse(int status, String accessToken,String userType,String name){
        super(status,userType,name);
        this.accessToken = accessToken;
    }
    //登陆成功
    //返回状态码、token、用户类型、名称

    public LoginResponse(int status, String accessToken,String mes){
        super(status,mes);
        this.accessToken = accessToken;
    }
    //登陆失败
}
