package com.arukione.curriculum_design.service;


import com.arukione.curriculum_design.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeanService {


 /*   final TopicInfoMapper topicInfoMapper;//课题信息获取
    final ApplicationMapper applicationMapper;//请求获取
    final StudentMapper studentMapper;//学生信息
    final TeacherMapper teacherMapper;//
    final UserService userService;*/
    final DeanMapper deanMapper;

    @Autowired
    DeanService(TopicInfoMapper topicInfoMapper, ApplicationMapper applicationMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, UserService userService, DeanMapper deanMapper) {
       /* this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper = applicationMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.userService = userService;*/
        this.deanMapper = deanMapper;
    }

}
