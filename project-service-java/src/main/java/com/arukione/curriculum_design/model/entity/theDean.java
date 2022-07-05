package com.arukione.curriculum_design.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.security.KeyException;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("dean")
public class theDean extends User{

    @TableId("Did")
    String Did;//系主任id
    @TableId("Dname")
    String Dname;//系主任姓名
    @TableId("Dpassword")
    String Dpassword;//密码
    @TableId("ProfID")
    String ProfID;//系主任专业id
    @TableId("phone")
    String phone;
    @TableId("email")
    String email;

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "Did":
                this.Did = value;
                break;
            case "Dname":
                this.Dname = value;
                break;
            case "Prof":
                this.ProfID = value;
                break;
            case "phone":
                this.phone = value;
                break;
            case "email":
                this.email =value;
                break;
            case "Dpassword":
                this.Dpassword = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }


}
