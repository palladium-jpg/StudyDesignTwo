package com.arukione.curriculum_design.model.DTO.Request;

import com.arukione.curriculum_design.model.entity.Student;
import lombok.Data;

@Data
public class NewStudent {
    String sid;
    String name;
    Integer grade;
    String professionId;
    Integer classNumber;
    String password;
    String accessToken;

    public Student convertStudent() {
        //学生数据的插入

        return Student.builder()
                .sid(sid)
                .name(name)
                .grade(grade)
                .professionId(professionId)
                .classNumber(classNumber)
                .password(password)
                .build();
    }
}
//新用户创建递送信息