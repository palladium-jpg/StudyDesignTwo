
package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.TheDean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DeanMapper extends BaseMapper<TheDean> {

    @Update("update dean set Did=#{value} where Did=#{Did}")
    int UpdateDid(String value,String Did);




}

