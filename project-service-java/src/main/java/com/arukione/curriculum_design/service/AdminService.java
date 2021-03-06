package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.*;
import com.arukione.curriculum_design.model.DTO.Request.NewDean;
import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Request.NewTeacher;
import com.arukione.curriculum_design.model.DTO.Response.AccountResponse;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.*;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.exception.PermissionException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.security.KeyException;
import java.util.List;

@Service
public class AdminService {

    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final UserService userService;
    final TopicInfoMapper topicInfoMapper;
    final ApplicationMapper applicationMapper;
    final DeanMapper deanMapper;


    @Autowired
    AdminService(StudentMapper studentMapper, TeacherMapper teacherMapper, UserService userService, TopicInfoMapper topicInfoMapper, ApplicationMapper applicationMapper, DeanMapper deanMapper) {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
        this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper = applicationMapper;
        this.deanMapper = deanMapper;
    }

    public Response adminPermission(String accessToken) {

        try {

            userService.permission(accessToken, "Admin");
            return null;

        } catch (PermissionException permissionException) {

            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);

        } catch (NullPointerException npe) {

            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);

        }
    }

    public Response addStudent(NewStudent newStudent) {

        Response response = adminPermission(newStudent.getAccessToken());
        //??????student??????????????????

        if (response != null)
            return response;
        //????????????

        Student student = newStudent.convertStudent();

        try {
            if (studentMapper.insert(student) == 1)
                //??????????????????????????????
                return new Response(HTTPStatus.Success);

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, "?????????????????????????????????");

        }

        return new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
    }

    public Response addTeacher(NewTeacher newTeacher) throws DataIntegrityViolationException {

        Response response = adminPermission(newTeacher.getAccessToken());
        //????????????????????????
        if (response != null)
            return response;

        Teacher teacher = newTeacher.convertTeacher();
        //?????????????????????
        System.out.println(teacher);

        try {
            if (teacherMapper.insert(teacher) == 1)
                //????????????????????????
                return new Response(HTTPStatus.Success);

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, "?????????????????????????????????");
        }
        return new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
    }
    //????????????

    //addDean
    public Response addDean(NewDean newDean){
        Response response=adminPermission(newDean.getAccessToken());
        if(response!=null)
            return response;
        TheDean theDean =newDean.convertDean();

        try{
            if(deanMapper.insert(theDean)==1)
                return new Response(HTTPStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new Response(HTTPStatus.Failed,"?????????????????????????????????");
        }
        return new Response(HTTPStatus.Failed,Message.DB_NOT_OPERATION);
    }

    //addDean

    public Response getAccounts(String accessToken) {

        Response response = adminPermission(accessToken);

        if (response != null) return response;

        List<Student> students = userService.getStudents(null);

        List<Teacher> teachers = userService.getTeachers(null);

        return new AccountResponse(HTTPStatus.OK, students, teachers);
    }
    //??????????????????token??????


    public Response deleteStudentByID(String accessToken, String SID) {
        try {

            return opsResult(accessToken, studentMapper.deleteById(SID), "????????????");

        } catch (Exception e) {
            if (e.getMessage().contains("foreign key")) {

                topicInfoMapper.updateSID(SID);

                QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();

                applicationQueryWrapper.eq("SID", SID);

                applicationMapper.delete(applicationQueryWrapper);

                return opsResult(accessToken, studentMapper.deleteById(SID), "????????????");
            } else {
                e.printStackTrace();
                return new Response(HTTPStatus.Failed, e.getMessage());
            }
        }
    }
    //??????????????????

    public Response deleteTeacherByID(String accessToken, String TID) {
        try {
            return opsResult(accessToken, teacherMapper.deleteById(TID), "????????????");
        } catch (Exception e) {
            if(e.getMessage().contains("foreign key")){
                QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
                topicQueryWrapper.eq("TID", TID);
                QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();
                List<Topic> topics = topicInfoMapper.selectList(topicQueryWrapper);
                for(Topic topic: topics){
                    applicationQueryWrapper.eq("TopicID", topic.getTopicId());
                    applicationQueryWrapper.or();
                }
                applicationMapper.delete(applicationQueryWrapper);
                topicInfoMapper.delete(topicQueryWrapper);
                return opsResult(accessToken, teacherMapper.deleteById(TID), "????????????");
            }else {
                e.printStackTrace();
                return new Response(HTTPStatus.Failed, e.getMessage());
            }
        }
    }
    //????????????

    //???????????????
    public Response deleteDeanByID(String accessToken,String DID){

        return opsResult(accessToken,teacherMapper.deleteById(DID),"????????????");
        //???????????????????????????
    }

    //???????????????


    private Response opsResult(String accessToken, int i, String message) {

        Response response = adminPermission(accessToken);

        if (response != null)
            return response;

        if (i == 1)
            return new Response(HTTPStatus.Success);
        else
            return new Response(HTTPStatus.Failed, message);
    }

    public Response changeStudentByID(String accessToken, String sid, String key, String value) {
        try {
            Student student = new Student();
            if (!key.equals("sid")) {
                student.setValue(key, value);
                student.setValue("sid", sid);
                return opsResult(accessToken, studentMapper.updateById(student), "????????????");
            } else {
                return opsResult(accessToken, studentMapper.updateSID(value, sid), "????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }
    //??????????????????

    public Response changeTeacherByID(String accessToken, String tid, String key, String value) {
        try {
            Teacher teacher = new Teacher();
            if (!key.equals("tid")) {
                teacher.setValue(key, value);
                teacher.setValue("tid", tid);
                return opsResult(accessToken, teacherMapper.updateById(teacher), "????????????");
            } else {
                return opsResult(accessToken, teacherMapper.updateTID(value, tid), "????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }
    //??????????????????

    //??????Dean??????
    public Response changeDeanByID(String accessToken,String DID,String key,String value){
        try{
            TheDean theDean=new TheDean();
            if(!key.equals("Did")){
                theDean.setValue(key,value);
                theDean.setValue("Did",DID);
                return opsResult(accessToken,deanMapper.updateById(theDean),"????????????");

            }else {
                return opsResult(accessToken,deanMapper.UpdateDid(value,key),"????????????");
            }
        } catch (KeyException e) {
            throw new RuntimeException(e);
        }
    }
    //??????Dean??????
}