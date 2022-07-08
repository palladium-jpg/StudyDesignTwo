package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.ApplicationMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.mapper.TopicTypeMapper;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTeacherResponse;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTopicResponse;
import com.arukione.curriculum_design.model.DTO.Response.UserInfoResponse;
import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import com.arukione.curriculum_design.model.entity.*;
import com.arukione.curriculum_design.utils.Generator;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arukione.curriculum_design.model.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    final UserService userService;
    final TopicInfoMapper topicInfoMapper;
    final TeacherMapper teacherMapper;
    final ApplicationMapper applicationMapper;
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    public StudentService(UserService userService, TopicInfoMapper topicInfoMapper, TeacherMapper teacherMapper, ApplicationMapper applicationMapper, TopicTypeMapper topicTypeMapper) {
        this.userService = userService;
        this.topicInfoMapper = topicInfoMapper;
        this.teacherMapper = teacherMapper;
        this.applicationMapper = applicationMapper;
        this.topicTypeMapper = topicTypeMapper;
    }

    public SelectableTopicResponse getAllTopic(String accessToken) {
        try {

            Student student = (Student) userService.permission(accessToken, "Student");
            //学生认证
            String profId = student.getProfessionId();
            //获取专业id
            ArrayList<SelectableTopicInfo> selectableTopicInfos = topicInfoMapper.getSelectableTopicInfo(profId);
            //数据库查询
            return new SelectableTopicResponse(HTTPStatus.OK, selectableTopicInfos);


        } catch (PermissionException permissionException) {
            return new SelectableTopicResponse(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new SelectableTopicResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }
    //可选课题浏览


    public SelectableTeacherResponse getAllTeacher(String accessToken) {
        try {
            Student student = (Student) userService.permission(accessToken, "Student");
            //用户认证
            String profId = student.getProfessionId();
            //获取学生专业
            QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
            //建立查询
            teacherQueryWrapper.eq("GuideProfID", profId);
            //查找专业相同的老师
            List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
            //进行查找，并将结果作为一个列表
            //selectList 查找符合条件的所有元素，已经在base中封装完成

            return new SelectableTeacherResponse(HTTPStatus.OK, teachers);
            //返回老师列表
        } catch (PermissionException permissionException) {
            return new SelectableTeacherResponse(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new SelectableTeacherResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }
    //

    public Response addApply(String accessToken, String topicId) {
            Response response = canApply(accessToken);

            if(response.getStatus()!=200)
                return response;

            String sid = response.getMessage();

            try {
                return getApplyResult(sid, topicId);

            }catch (Exception e){
                if(e.getMessage().contains("for key 'PRIMARY'"))

                    return new Response(HTTPStatus.Failed, "不能再次申请该课题");

                else{
                    e.printStackTrace();

                    return new Response(HTTPStatus.Failed, e.getMessage());
                }
            }
    }
    //发送请求，倒是课题申请

    public Response addApply(String accessToken, String tid, TopicInfo topicInfo) {
            Response response = canApply(accessToken);
            if(response.getStatus()!=200) return response;
            String sid = response.getMessage();
            String topicId = Generator.generateTopicID();
            Topic topic = Topic.builder()
                    .topicId(topicId)
                    .topicName(topicInfo.getTopicName())
                    .introduction(topicInfo.getIntroduction())
                    .tid(tid)
                    .sid(sid)
                    .typeId(topicInfo.getTypeId())
                    .source("1")
                    .build();
            try {
                if (topicInfoMapper.insert(topic) != 1) return new Response(HTTPStatus.Failed, Message.DB_NO_DATA);
                return getApplyResult(sid, topicId);
            } catch (Exception e){
                if(e.getMessage().contains("for key 'PRIMARY'"))
                    return new Response(HTTPStatus.Failed, "不能再次申请该课题");
                else{
                    e.printStackTrace();
                    return new Response(HTTPStatus.Failed, e.getMessage());
                }
            }
    }
    //自选课题申请

    private Response canApply(String accessToken) {
        try {
            Student student = (Student) userService.permission(accessToken, "Student");

            QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();

            applicationQueryWrapper.eq("SID", student.getSid()).eq("Status", "1");
            //查询学生的请求，查找学生是否已经成功的申请到课题，为1则说明成功

            if (applicationMapper.selectOne(applicationQueryWrapper) != null)
                return new Response(HTTPStatus.Failed, "你已有课题，无法再申请");
            //状态码为1时说明已经拥有课题
            //在请求表中查询
            return new Response(HTTPStatus.OK, student.getSid());

        } catch (PermissionException permissionException) {

            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {

            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    private Response getApplyResult(String sid, String topicId) {
        Application application = new Application();
        application.setSid(sid);
        application.setTopicId(topicId);
        application.setApplyTime(Generator.getNowTime());
        application.setStatus("0");
        return userService.opsResult(applicationMapper.insert(application));
    }
    //插入新的请求，若一个学生没有课题，则在状态为1


    //获取我的导师信息
    public UserInfoResponse getMyTeacher(String accessToken) {
        try {
            Student stu = (Student) userService.permission(accessToken, "Student");
            //获取已通过的申请记录
            QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();

            applicationQueryWrapper.eq("SID", stu.getSid()).eq("Status", "1");
            //查找成功且可用的申请
            Application application = applicationMapper.selectOne(applicationQueryWrapper);

            if(application == null)
                return new UserInfoResponse(HTTPStatus.Failed, Message.DB_NO_DATA);
            //为null则说明没有成功的请求
            Topic topic = topicInfoMapper.selectById(application.getTopicId());

            return new UserInfoResponse(200, teacherMapper.selectById(topic.getTid()));
        } catch (PermissionException permissionException) {
            return new UserInfoResponse(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new UserInfoResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }



    //获得已通过的课题信息
    public Map<String, Object> getAllowTopic(String accessToken) {
        Map<String, Object> response = new HashMap<>();

        try {
            Student stu = (Student) userService.permission(accessToken, "Student");

            QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("SID", stu.getSid()).eq("Source", 0);

            Topic topic = topicInfoMapper.selectOne(queryWrapper);

            if(topic==null){

                QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();

                applicationQueryWrapper.eq("SID", stu.getSid()).eq("Status", "1");

                Application application = applicationMapper.selectOne(applicationQueryWrapper);

                System.out.println(application);

                if (application == null) {

                    response.put("status", HTTPStatus.Failed);

                    response.put("message","没有获取到数据");

                    return response;

                }

                topic = topicInfoMapper.selectById(application.getTopicId());
            }


            TopicType type = topicTypeMapper.getTopicType(topic.getTypeId());

            response.put("status", HTTPStatus.OK);

            response.put("topic",topic);

            response.put("type",type);

            Teacher teacher = teacherMapper.selectById(topic.getTid());

            response.put("name", teacher.getName());

            return response;
        } catch (PermissionException permissionException) {
            response.put("status", HTTPStatus.NotAllowed);
            response.put("message",Message.USER_PERMISSION_ERROR);
            return response;
        } catch (NullPointerException npe) {
            response.put("status", HTTPStatus.Unauthorized);
            response.put("message",Message.NO_LOGIN_STATUS);
            return response;
        }
    }


    public Map<String, Object> getApplicationInfo(String accessToken) {
        Map<String, Object> response = new HashMap<>();
        try {
            Student stu = (Student) userService.permission(accessToken, "Student");
            ArrayList<Application> applies = applicationMapper.getApplicationsOfSID(stu.getSid());
            if (applies == null || applies.size()==0) {
                response.put("status", HTTPStatus.Failed);
                response.put("message","没有获取到数据");
                return response;
            }
            ArrayList<Object> mapData =new ArrayList<>();
            for (Application apply : applies) {
                Map<String, Object> data = new HashMap<>();
                Topic topic =  topicInfoMapper.selectById(apply.getTopicId());
                data.put("topicInfo", topic);
                data.put("teacherName", teacherMapper.selectById(topic.getTid()).getName());
                data.put("applyInfo", apply);
                mapData.add(data);
            }
            response.put("status", HTTPStatus.OK);
            response.put("applyRecord", mapData);
            return response;
        } catch (PermissionException permissionException) {
            response.put("status", HTTPStatus.NotAllowed);
            response.put("message",Message.USER_PERMISSION_ERROR);
            return response;
        } catch (NullPointerException npe) {
            response.put("status", HTTPStatus.Unauthorized);
            response.put("message",Message.NO_LOGIN_STATUS);
            return response;
        }
    }
}
