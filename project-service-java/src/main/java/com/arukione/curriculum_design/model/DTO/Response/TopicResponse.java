package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.TopicView;

import java.util.ArrayList;

public class TopicResponse extends Response{
    ArrayList<TopicView> topicViews;

    public TopicResponse(int status) {
        super(status);
    }




    public TopicResponse(int status, ArrayList<TopicView> topicViews) {
        super(status);
        this.topicViews = topicViews;
    }
}
