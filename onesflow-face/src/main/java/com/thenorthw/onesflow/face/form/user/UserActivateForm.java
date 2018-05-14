package com.thenorthw.onesflow.face.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by theNorthW on ${date}.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class UserActivateForm {
    @Pattern(regexp = ".*\\..*\\..*")
    @NotNull
    String activateToken;

    public String getActivateToken() {
        return activateToken;
    }

    public void setActivateToken(String activateToken) {
        this.activateToken = activateToken;
    }
}
