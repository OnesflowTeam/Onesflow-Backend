package com.thenorthw.onesflow.face.form.blog.article;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class GroupChangePostForm {
    @NotNull
    @Pattern(regexp = "[1-9]\\d*")
    String articleId;

    @NotNull
    @Pattern(regexp = "[1-9]\\d*")
    String groupId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
