package com.arukione.curriculum_design.model.VO;

import lombok.Data;

@Data
public class ApplicationStatusInfo {
    //学生申请课题情况视图
    String TopicName;
    String SName;
    String ApplyTime;
    String Status;
    String Source;
}
