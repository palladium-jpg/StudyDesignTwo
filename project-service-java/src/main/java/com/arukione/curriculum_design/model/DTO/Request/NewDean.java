package com.arukione.curriculum_design.model.DTO.Request;

import com.arukione.curriculum_design.model.entity.TheDean;
import lombok.Data;


@Data
public class NewDean {

    String Did;//系主任id

    String Dname;//系主任姓名

    String Dpassword;//密码

    String ProfID;//系主任专业id

    String phone;

    String email;

    String accessToken;

    public TheDean convertDean(){

        return TheDean.builder()
                .Did(Did)
                .Dname(Dname)
                .Dpassword(Dpassword)
                .ProfID(ProfID)
                .phone(phone)
                .email(email)
                .build();
    }

}
