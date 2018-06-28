package com.thenorthw.onesflow.face.form.blog.comment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by theNorthW on 22/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class BlogCommentPostForm {
    @NotNull
    @Pattern(regexp = "[1-9]\\d*")
    Long aid;

    @NotNull
    @Size(min = 1,max = 188)
    String content;

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
}
