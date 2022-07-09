package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.NewDean;
import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Request.NewTeacher;
import com.arukione.curriculum_design.model.DTO.Response.AccountResponse;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    final AdminService adminService;

    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("addStudent")
    public Response addStudent(@RequestBody NewStudent newStudent) {
        return adminService.addStudent(newStudent);
    }

    @PostMapping("addTeacher")
    public Response addTeacher(@RequestBody NewTeacher newTeacher) {
        return adminService.addTeacher(newTeacher);
    }

    @PostMapping("addDean")
    public Response addDean(@RequestBody NewDean newDean){
        return adminService.addDean(newDean);
    }

    @GetMapping("getAccount")
    public AccountResponse getAccount(@RequestParam("accessToken") String accessToken) {
        return (AccountResponse) adminService.getAccounts(accessToken);
    }

    @DeleteMapping("deleteStudent")
    public Response deleteStudent(@RequestParam("accessToken") String accessToken, @RequestParam("sid") String sid) {
        return adminService.deleteStudentByID(accessToken, sid);
    }

    @DeleteMapping("deleteTeacher")
    public Response deleteTeacher(@RequestParam("accessToken") String accessToken, @RequestParam("tid") String tid) {
        return adminService.deleteTeacherByID(accessToken, tid);
    }

    @DeleteMapping("deleteDean")
    public Response deleteDean(@RequestParam("accessToken")String accessToken,@RequestParam("Did") String Did){
        return adminService.deleteDeanByID(accessToken,Did);
    }
    //删除Dean

    @PostMapping("changeStudentInfo")
    public Response changeStudentInfo(@RequestParam("accessToken") String accessToken,
                                      @RequestParam("sid") String sid,
                                      @RequestParam("key") String key,
                                      @RequestParam("value") String value) {
        return adminService.changeStudentByID(accessToken, sid, key, value);
    }

    @PostMapping("changeTeacherInfo")
    public Response changeTeacherInfo(@RequestParam("accessToken") String accessToken,
                                      @RequestParam("tid") String tid,
                                      @RequestParam("key") String key,
                                      @RequestParam("value") String value) {
        return adminService.changeTeacherByID(accessToken, tid, key, value);
    }

    //更改系主任
    @PostMapping("changeDeanInfo")
    public Response changeDeanInfo(
            @RequestParam("accessToken") String accessToken,
            @RequestParam("Did") String Did,
            @RequestParam("key") String key,
            @RequestParam("value") String value
    )
    {
        return adminService.changeDeanByID(accessToken,Did,key,value);
    }
}
