package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("profession")
@Data
public class Profession {
    @TableId("ProfID")
    String id;//专业id
    @TableField("ProfName")
    String name;//专业名称
    @TableField("DeptID")
    String deptId;//学院id
    @TableField(exist = false)
    String deptName;
}
