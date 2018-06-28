package com.thenorthw.onesflow.face.form.blog.common;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class BlogAboutMeForm {
    @Size(min=1,max = 1080)
    @NotNull
    String aboutMe;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
