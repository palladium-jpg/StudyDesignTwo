package com.arukione.curriculum_design.model.VO;

import lombok.Data;

@Data
public class StudentWithTeacher {
    String ApplyTime;
    //学生
    String SName;
    String Grade;
    String ProfName;//
    String ClassNumber;
    //课题信息
    String TypeName;
    String Introduction;
    String TopicName;
    //老师
    String Position;
    String Rank;
    String TName;//教师名字
}
