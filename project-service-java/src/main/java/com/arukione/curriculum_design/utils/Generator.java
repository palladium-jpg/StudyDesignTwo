package com.arukione.curriculum_design.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Generator {

    public static String generateAccessToken() {
        return UUID.randomUUID().toString();
        //自动生成主键，充当于身份验证中的token
    }//生成token

    public static String generateTopicID() {
        long time = new Date().getTime()/10;
        return String.valueOf(time);
    }//生成选题的id

    public static String getNowTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        return format.format(date);
    }//获取当前的时间
}
