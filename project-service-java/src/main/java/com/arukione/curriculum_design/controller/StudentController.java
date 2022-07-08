package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTeacherResponse;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTopicResponse;
import com.arukione.curriculum_design.model.DTO.Response.UserInfoResponse;
import com.arukione.curriculum_design.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StudentController {

    final StudentService studentService;

    @Autowired//注入定义好的bean
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getSelectableTopic")
    public SelectableTopicResponse getSelectableTopic(@RequestParam("accessToken") String accessToken) {
        return studentService.getAllTopic(accessToken);
    }
    //获取所有的可选的topic

    @GetMapping("/getSelectableTeacher")
    public SelectableTeacherResponse getSelectableTeacher(@RequestParam("accessToken") String accessToken) {
        return studentService.getAllTeacher(accessToken);
    }
    //获取所有的可选老师

    @PostMapping("/applyTeacherTopic")
    public Response applyTeacherTopic(@RequestParam("accessToken") String accessToken, @RequestParam("topicId") String topicId) {
        return studentService.addApply(accessToken, topicId);
    }
    //发送申请老师的论文的请求

    @PostMapping("applyStudentTopic")
    public Response applyStudentTopic(@RequestParam("accessToken") String accessToken, @RequestParam("tid") String tid, @RequestBody TopicInfo topicInfo) {
        return studentService.addApply(accessToken, tid, topicInfo);
    }
    //向老师发送申请自定义topic的请求

    //获得我的导师信息 √
    @GetMapping("getMyTeacher")
    public UserInfoResponse getMyTeacher(@RequestParam("accessToken") String accessToken) {
        return studentService.getMyTeacher(accessToken);
    }

    //获取已通过课题 √
    @GetMapping("getAllowTopic")
    public Map<String, Object> getAllowTopic(@RequestParam("accessToken") String accessToken) {
        return studentService.getAllowTopic(accessToken);
    }

    //获取申请记录 √
    @GetMapping("getApplyRecord")
    public Map<String, Object> getApplicationInfo(@RequestParam("accessToken") String accessToken) {
        return studentService.getApplicationInfo(accessToken);
    }
}
