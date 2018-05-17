package com.thenorthw.onesflow.web.controller.user;

import com.auth0.jwt.interfaces.Claim;
import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.user.UserLoginForm;
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
import java.util.Map;

/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class LoginController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel login(@Valid UserLoginForm userLoginForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        if(userLoginForm.getLogintype().equals("1")){
            //通过账号密码登录
            User user = userService.userLoginByLP(userLoginForm.getLoginname(),userLoginForm.getPassword());

            if(user == null){
                responseModel.setResponseCode(ResponseCode.NO_SUCH_ACCOUNT_OR_PASSWORD_WRONG.getCode());
                responseModel.setMessage(ResponseCode.NO_SUCH_ACCOUNT_OR_PASSWORD_WRONG.getMessage());
            }else if(user.getGmtActivate() == null){
                //未激活账号，不予登录
                responseModel.setResponseCode(ResponseCode.HAVE_NOT_ACTIVATED.getCode());
                responseModel.setMessage(ResponseCode.HAVE_NOT_ACTIVATED.getMessage());
            }else{
                //在response header中放入x-token
                httpServletResponse.addHeader(OnesflowConstant.TOKEN_HEADER, JwtUtil.createToken(user.getId().toString(),userLoginForm.getRemember().equals("1")));
                responseModel.setData(user);
            }
        }

        return responseModel;
    }

    @RequestMapping(value = "/user/login/token",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel loginByToken() {
        ResponseModel responseModel = new ResponseModel();

        User user = userService.userLoginByToken(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER));

        if(user == null){
            responseModel.setResponseCode(ResponseCode.UNAUTHORIZED.getCode());
            responseModel.setMessage(ResponseCode.UNAUTHORIZED.getMessage());
        }else if(user.getGmtActivate() == null){
            //未激活账号，不予登录
            responseModel.setResponseCode(ResponseCode.HAVE_NOT_ACTIVATED.getCode());
            responseModel.setMessage(ResponseCode.HAVE_NOT_ACTIVATED.getMessage());
        }else {
            responseModel.setData(user);
        }

        return responseModel;
    }
}
