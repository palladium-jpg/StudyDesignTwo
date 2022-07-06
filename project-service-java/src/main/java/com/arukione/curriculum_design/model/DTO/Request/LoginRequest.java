package com.arukione.curriculum_design.model.DTO.Request;

import lombok.Data;

@Data
public class LoginRequest {
    String userType;
    String userId;
    String password;
}
//登录请求时发送的信息格式