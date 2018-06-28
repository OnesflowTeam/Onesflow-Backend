package com.thenorthw.onesflow.common.model.blog.comment;

import java.util.Date;

/**
 * Created by theNorthW on 22/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class BlogComment {
    Long id;
    Long uid;
    Long aid;
    String content;

    Date gmtCreate;
    Date gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
