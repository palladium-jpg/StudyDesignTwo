package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoResponse extends Response {

    Admin adminInfo;
    Teacher teacherInfo;
    Student studentInfo;

    TheDean theDean;

    public UserInfoResponse(int status, Admin admin) {
        super(status);
        this.adminInfo = admin;
    }

    public UserInfoResponse(int status, Student student) {
        super(status);
        this.studentInfo = student;
    }

    public UserInfoResponse(int status, Teacher teacher){
        super(status);
        this.teacherInfo = teacher;
    }

    public UserInfoResponse(int status, TheDean theDean){
        super(status);
        this.theDean=theDean;
    }

    public UserInfoResponse(int status, String message){
        super(status, message);
    }

}
