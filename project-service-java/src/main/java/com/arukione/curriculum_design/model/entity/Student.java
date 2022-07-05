package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.security.KeyException;

@TableName("student")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @TableId("SID")
    String sid; //学生编号
    @TableField("SName")
    String name;//学生姓名
    @TableField("Grade")
    Integer grade;//入学年份
    @TableField("ProfID")
    String professionId;//专业id
    @TableField("ClassNumber")
    Integer classNumber;//班级id
    @TableField("Password")
    String password;//密码

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "sid":
                this.sid = value;
                break;
            case "name":
                this.name = value;
                break;
            case "professionId":
                this.professionId = value;
                break;
            case "grade":
                this.grade = Integer.parseInt(value);
                break;
            case "classNumber":
                this.classNumber = Integer.parseInt(value);
                break;
            case "password":
                this.password = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
