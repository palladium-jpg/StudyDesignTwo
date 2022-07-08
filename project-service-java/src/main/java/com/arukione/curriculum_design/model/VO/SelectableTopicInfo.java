package com.arukione.curriculum_design.model.VO;

import lombok.Data;

@Data
public class SelectableTopicInfo {
    String topicId;//课题id
    String topicName;//课题名字
    String TID;//教师id
    String TName;
    String typeName;
    String introduction;

}
//课题信息
//学生选择课题时所看见的视图
