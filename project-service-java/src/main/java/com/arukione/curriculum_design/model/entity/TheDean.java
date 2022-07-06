package com.arukione.curriculum_design.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.stereotype.Component;

import java.security.KeyException;

@ToString
@Data
@Component
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("dean")
@Builder
public class TheDean extends User{

    @TableId("Did")
    String Did;//系主任id
    @TableField("Dname")
    String Dname;//系主任姓名
    @TableField("Dpassword")
    String Dpassword;//密码
    @TableField("ProfID")
    String ProfID;//系主任专业id
    @TableField("phone")
    String phone;
    @TableField("email")
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
