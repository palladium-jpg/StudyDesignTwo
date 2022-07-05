package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.security.KeyException;

@TableName("teacher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    @TableId("TID")
    String tid;//教师id
    @TableField("TName")
    String name;//名称
    @TableField("Position")
    String position;
    @TableField("TRank")
    String trank;//职称
    @TableField("GuideProfID")
    String guideProfID;//教师专业id
    @TableField("Phone")
    String phone;//电话号码
    @TableField("Email")
    String email;//邮件
    @TableField("TopicDemand")
    String topicDemand;//
    @TableField("Password")
    String password; //密码

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "tid":
                this.tid = value;
                break;
            case "guideProfID":
                this.guideProfID = value;
                break;
            case "position":
                this.position = value;
                break;
            case "trank":
                this.trank = value;
                break;
            case "phone":
                this.phone = value;
                break;
            case "email":
                this.email = value;
                break;
            case "topicDemand":
                this.topicDemand = value;
                break;
            case "password":
                this.password = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
