package com.thenorthw.onesflow.web.controller.advice;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.advice.AddAdviceForm;
import com.thenorthw.onesflow.face.form.user.UserLoginForm;
import com.thenorthw.onesflow.web.service.advice.AdviceService;
import com.thenorthw.onesflow.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class AdviceController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @Autowired
    AdviceService adviceService;

    @RequestMapping(value = "/advice",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel addAdvice(@Valid AddAdviceForm addAdviceForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        int result = adviceService.addAdvice(addAdviceForm.getEmail(),addAdviceForm.getAdvice());

        if(result != 1){
            responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        return responseModel;
    }
}
