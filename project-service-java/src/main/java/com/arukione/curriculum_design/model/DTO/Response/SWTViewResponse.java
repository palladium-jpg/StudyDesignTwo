package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.StudentWithTeacher;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)

public class SWTViewResponse extends Response{


    ArrayList<StudentWithTeacher> studentWithTeachers;
    public SWTViewResponse(int status,String mes) {
        super(status,mes);
    }

    public SWTViewResponse(int status, ArrayList<StudentWithTeacher>studentWithTeachers) {
        super(status);
        this.studentWithTeachers=studentWithTeachers;
    }
}
