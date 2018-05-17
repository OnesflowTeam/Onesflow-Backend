package com.thenorthw.onesflow.face.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by theNorthW on 17/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class UserResetpForm {
    @Pattern(regexp = ".*\\..*\\..*")
    @NotNull
    String resetpToken;

    @Pattern(regexp = "^[a-fA-F0-9]{32}$")
    @NotNull
    String newpass;

    public String getResetpToken() {
        return resetpToken;
    }

    public void setResetpToken(String resetpToken) {
        this.resetpToken = resetpToken;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
}
