package com.user.solrcenter.bens;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class User {
    @Field
    private String id;
    @Field
    private Date feedbackTime;
    @Field
    private String content;
    @Field
    private String userinfoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(String userinfoId) {
        this.userinfoId = userinfoId;
    }
}
