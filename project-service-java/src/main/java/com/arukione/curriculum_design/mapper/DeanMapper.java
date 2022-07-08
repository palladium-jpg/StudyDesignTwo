
package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.VO.StudentWithTeacher;
import com.arukione.curriculum_design.model.VO.TopicView;
import com.arukione.curriculum_design.model.entity.TheDean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DeanMapper extends BaseMapper<TheDean> {

    @Update("update dean set Did=#{value} where Did=#{Did}")
    int UpdateDid(String value,String Did);

    @Select(" select ApplyTime,SName,Grade,ProfName,ClassNumber,TName,Position,TRank,TopicName,TypeName,Introduction " +
            "from application,student,topic_info,profession,topic_type,teacher " +
            "where Status='1' " +
            "and application.SID=student.SID " +
            "and application.TopicId=topic_info.TopicID " +
            "and student.ProfID=profession.ProfID " +
            "and topic_info.TypeID=topic_type.TypeID " +
            "and topic_info.TID=teacher.TID;")
    ArrayList<StudentWithTeacher> SelectInfoAboutView();

    @Delete("delete from topic_info where TopicID=#{TopicID}")
    int DeleteTopicByTopicID(String TopicID);
    // 删除选定的论文课题
    @Select("select TopicID,TopicName,Introduction,TypeName,student.SID,SName " +
            "from student,teacher,topic_info,topic_type " +
            "where topic_info.TypeID=topic_type.TypeID and " +
            "topic_info.TID=teacher.TID  and " +
            "student.SID=topic_info.SID and" +
            "teacher.TID=#{ProfID};")
    ArrayList<TopicView> SelectTopicByProfID(String ProfId);
}

