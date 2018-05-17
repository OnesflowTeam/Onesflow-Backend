package com.thenorthw.onesflow.web.service.mail;

import com.thenorthw.onesflow.common.model.mail.MailRecord;
import com.thenorthw.onesflow.common.model.user.User;

/**
 * Created by theNorthW on 14/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public interface MailService {
    public int sendActivateMailAfterRegister(User user, String token);

    public int sendActivateMailAgain(String email);


    public int sendResetpMail(String email);
}
