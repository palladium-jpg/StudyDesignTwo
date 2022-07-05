package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.KeyException;

@TableName("topic_info")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @TableId(value = "TopicID")
    String topicId;//选题的id
    @TableField("TopicName")
    String topicName;//选题名称
    @TableField("Introduction")
    String introduction;//选题介绍
    @TableField("TID")
    String tid;//教师id
    @TableField("TypeID")
    String typeId;//选题类型
    @TableField("Source")
    String source;
    @TableField("SID")
    String sid;

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "topicName":
                this.topicName = value;
                break;
            case "introduction":
                this.introduction = value;
                break;
            case "type":
                this.typeId = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
