package com.arukione.curriculum_design.service;


import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.*;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SWTViewResponse;
import com.arukione.curriculum_design.model.DTO.Response.TopicResponse;
import com.arukione.curriculum_design.model.DTO.Response.TopicTResponse;
import com.arukione.curriculum_design.model.VO.StudentWithTeacher;
import com.arukione.curriculum_design.model.VO.TopicView;
import com.arukione.curriculum_design.model.entity.Application;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.TheDean;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeanService {

    final TopicInfoMapper topicInfoMapper;//课题信息获取
    final ApplicationMapper applicationMapper;//请求获取
    final StudentMapper studentMapper;//学生信息
    final TeacherMapper teacherMapper;//
    final UserService userService;
    final DeanMapper deanMapper;


    @Autowired
    DeanService(TopicInfoMapper topicInfoMapper, ApplicationMapper applicationMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, UserService userService, DeanMapper deanMapper) {this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper = applicationMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
        this.deanMapper = deanMapper;
    }

    public SWTViewResponse getInfoAboutTopic(String accessToken){
        try{
            TheDean theDean =(TheDean) userService.permission(accessToken,"Dean");
            String ProfId=theDean.getProfID();
            ArrayList<StudentWithTeacher> studentWithTeacherLis=deanMapper.SelectInfoAboutView();

            return new SWTViewResponse(HTTPStatus.OK,studentWithTeacherLis);

        } catch (PermissionException e) {
            throw new RuntimeException(e);
        }
    }
    //查看所有的已经选择好的习题结果与相关信息
    public Response DeleteTopicByID(String accessToken,String TopicID){
       if(DeanPermission(accessToken) == null){
           deanMapper.deleteById(TopicID);
           return new Response(HTTPStatus.OK,"删除成功");
       }else {
           return new Response(HTTPStatus.Failed,"删除失败");
       }


    }
     public TopicResponse GetTopicByProfID(String accessToken) throws PermissionException {
         TheDean theDean =(TheDean) userService.permission(accessToken,"Dean");
         String ProfId=theDean.getProfID();
         ArrayList<TopicView> topicViews=deanMapper.SelectTopicByProfID(ProfId);
         return new TopicResponse(HTTPStatus.OK,topicViews);
     }




    private Response opsResult(String accessToken, int i, String message) {

        Response response = DeanPermission(accessToken);

        if (response != null)
            return response;

        if (i == 1)
            return new Response(HTTPStatus.Success);
        else
            return new Response(HTTPStatus.Failed, message);
    }

    public Response DeanPermission(String accessToken) {

        try {

            userService.permission(accessToken, "Dean");
            return null;

        } catch (PermissionException permissionException) {

            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);

        } catch (NullPointerException npe) {

            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);

        }
    }



}
