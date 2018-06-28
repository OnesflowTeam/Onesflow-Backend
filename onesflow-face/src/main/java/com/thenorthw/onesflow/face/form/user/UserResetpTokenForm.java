package com.thenorthw.onesflow.face.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by theNorthW on 17/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class UserResetpTokenForm {
    @Pattern(regexp = ".*\\..*\\..*")
    @NotNull
    String resetpToken;

    public String getResetpToken() {
        return resetpToken;
    }

    public void setResetpToken(String resetpToken) {
        this.resetpToken = resetpToken;
    }
}
