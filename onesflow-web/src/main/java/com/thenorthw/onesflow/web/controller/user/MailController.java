package com.thenorthw.onesflow.web.controller.user;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.face.form.mail.MailAddressForm;
import com.thenorthw.onesflow.web.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by theNorthW on 14/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class MailController {
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/mail/activate",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel sendActivateMail(@Valid MailAddressForm mailAddressForm , BindingResult bindingResult){
        ResponseModel responseModel = new ResponseModel();

        int result = mailService.sendActivateMailAgain(mailAddressForm.getEmail());
        if(result == 0){
            responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }else if(result == -1){
            responseModel.setResponseCode(ResponseCode.NO_SUCH_USER.getCode());
            responseModel.setMessage(ResponseCode.NO_SUCH_USER.getMessage());
        }else if(result == -2){
            responseModel.setResponseCode(ResponseCode.ACTIVATE_MAIL_SEND_TOO_FREQUENTLY.getCode());
            responseModel.setMessage(ResponseCode.ACTIVATE_MAIL_SEND_TOO_FREQUENTLY.getMessage());
        }

        return responseModel;
    }
}
