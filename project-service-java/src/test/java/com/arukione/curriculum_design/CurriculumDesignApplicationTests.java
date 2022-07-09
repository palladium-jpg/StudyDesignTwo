package com.arukione.curriculum_design;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.mapper.DeanMapper;
import com.arukione.curriculum_design.mapper.ProfessionMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.DTO.Response.SWTViewResponse;
import com.arukione.curriculum_design.model.VO.StudentWithTeacher;
import com.arukione.curriculum_design.model.entity.Admin;
import com.arukione.curriculum_design.model.entity.Profession;
import com.arukione.curriculum_design.model.entity.TheDean;
import com.arukione.curriculum_design.service.DeanService;
import com.arukione.curriculum_design.service.UserService;
import com.arukione.curriculum_design.utils.Generator;
import com.arukione.curriculum_design.utils.HTTPStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CurriculumDesignApplicationTests {

	DeanService deanService;
	DeanMapper deanMapper;
	public SWTViewResponse tets(){
		ArrayList<StudentWithTeacher> studentWithTeacherLis=deanMapper.SelectInfoAboutView();
		for(StudentWithTeacher studentWithTeacher:studentWithTeacherLis){
			System.out.println(studentWithTeacher);
		}
		return new SWTViewResponse(HTTPStatus.OK,studentWithTeacherLis);
	}

}
