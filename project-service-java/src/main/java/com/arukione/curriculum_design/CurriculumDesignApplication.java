package com.arukione.curriculum_design;

import com.arukione.curriculum_design.mapper.DeanMapper;
import com.arukione.curriculum_design.model.VO.StudentWithTeacher;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
@MapperScan("com.arukione.curriculum_design.mapper")
public class CurriculumDesignApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurriculumDesignApplication.class, args);
	}

}
