package com.thenorthw.onesflow.common.model.blog.common;

import java.util.Date;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class BlogCommon {
    Long id;

    Long uid;

    String blogIntro;

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

    public String getBlogIntro() {
        return blogIntro;
    }

    public void setBlogIntro(String blogIntro) {
        this.blogIntro = blogIntro;
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
