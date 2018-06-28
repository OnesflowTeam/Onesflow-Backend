package com.thenorthw.onesflow.face.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class UserChangePasswordForm {
    @Pattern(regexp = "^[a-fA-F0-9]{32}$")
    @NotNull
    String oldpass;

    @Pattern(regexp = "^[a-fA-F0-9]{32}$")
    @NotNull
    String newpass;

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
}
