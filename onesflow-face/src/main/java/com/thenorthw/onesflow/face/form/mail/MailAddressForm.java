package com.thenorthw.onesflow.face.form.mail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Created by theNorthW on 14/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class MailAddressForm {
    @NotNull
    @Email
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
