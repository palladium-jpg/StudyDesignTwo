package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("department")
@Data
public class Department {
    @TableId("DeptID")
    String deptId;//学院id
    @TableField("DeptName")
    String name;//学院名称
}
