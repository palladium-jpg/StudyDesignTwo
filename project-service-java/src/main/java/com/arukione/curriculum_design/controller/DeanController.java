
package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.DeanMapper;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SWTViewResponse;
import com.arukione.curriculum_design.model.DTO.Response.TopicResponse;
import com.arukione.curriculum_design.model.DTO.Response.TopicTResponse;
import com.arukione.curriculum_design.model.entity.Topic;
import com.arukione.curriculum_design.service.DeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeanController {
    final DeanService deanService;
    @Autowired
    DeanController(DeanService deanService){
        this.deanService=deanService;
    }

    @PostMapping("ListOfDatails")
    public SWTViewResponse getselectDetail(@RequestParam("accessToken") String accessToken){
        return deanService.getInfoAboutTopic(accessToken);
    }
    @PostMapping("DeleteTopic")
    public Response deleteTopicById(@RequestParam("accessToken") String accessToken,@RequestParam("topicId") String TopicID){
        return deanService.DeleteTopicByID(accessToken,TopicID);
    }

    @PostMapping("ListTopicOfProfID")
    public TopicResponse GetTopicInfo(@RequestParam("accessToken") String accessToken) throws PermissionException {
        return deanService.GetTopicByProfID(accessToken);
    }

    @GetMapping("/allTeacher")
    public Response allTeacher(@RequestParam("accessToken") String accessToken) {
        return deanService.allTeacher(accessToken);
    }

    @GetMapping( "editTopic")
    public Response editTopic(@RequestParam("accessToken") String accessToken){
        return deanService.editTopic(accessToken);
    }

    @GetMapping( "getTopicType")
    public Response getTopicType(@RequestParam("accessToken") String accessToken){
        return deanService.getTopicType(accessToken);
    }

    @GetMapping( "getStudentMessage")
    public Response getStudentApplication(@RequestParam("accessToken") String accessToken){
        return deanService.getStudentApplication(accessToken);
    }



}

