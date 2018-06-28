package com.thenorthw.onesflow.web.controller.user;

import com.auth0.jwt.interfaces.Claim;
import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.user.UserChangePasswordForm;
import com.thenorthw.onesflow.face.form.user.UserInfoUpdateForm;
import com.thenorthw.onesflow.face.form.user.UserResetpForm;
import com.thenorthw.onesflow.face.form.user.UserResetpTokenForm;
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
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class PasswordController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @Autowired
    UserService userService;


    @RequestMapping(value = "/user/password/new",method = RequestMethod.POST)
    @ResponseBody
    @LoginNeed
    public ResponseModel changePassword(@Valid UserChangePasswordForm userChangePasswordForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        String token = httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER);

        Long uid = JwtUtil.getUidFromClaims(JwtUtil.verify(token));

        int result = userService.changePassword(uid,userChangePasswordForm.getOldpass(),userChangePasswordForm.getNewpass());

        //在更新密码后其实要让客户端存储的token无效，
        //1. 数据库中存储每次颁发的token，在aop中每次进行查询数据库来比较
        //   或者利用缓存来避免每次到数据库中查询token
        //   暂时不实现
        if(result != 1){
            responseModel.setResponseCode(ResponseCode.WRONG_OLD_PASSWORD.getCode());
            responseModel.setMessage(ResponseCode.WRONG_OLD_PASSWORD.getMessage());
        }

        return responseModel;
    }

    @RequestMapping(value = "/user/password/resetp/token/check",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel changePassword(@Valid UserResetpTokenForm userResetpTokenForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        Map<String,Claim> cs = JwtUtil.verifyResetp(userResetpTokenForm.getResetpToken());

        if(cs == null){
            responseModel.setResponseCode(ResponseCode.ILLEGAL_RESETP_TOKEN.getCode());
            responseModel.setMessage(ResponseCode.ILLEGAL_RESETP_TOKEN.getMessage());
        }

        return responseModel;
    }

    @RequestMapping(value = "/user/password/resetp",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel changePasswordWithToken(@Valid UserResetpForm userResetpForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        Map<String,Claim> cs = JwtUtil.verifyResetp(userResetpForm.getResetpToken());

        if(cs == null){
            responseModel.setResponseCode(ResponseCode.ILLEGAL_RESETP_TOKEN.getCode());
            responseModel.setMessage(ResponseCode.ILLEGAL_RESETP_TOKEN.getMessage());
        }else {
            if(cs.get("t") == null || !cs.get("t").asString().equals("r")){
                responseModel.setResponseCode(ResponseCode.ILLEGAL_RESETP_TOKEN.getCode());
                responseModel.setMessage(ResponseCode.ILLEGAL_RESETP_TOKEN.getMessage());
            }else {
                int result = userService.changePasswordByToken(Long.parseLong(cs.get("u").asString()),userResetpForm.getNewpass());

                //在更新密码后其实要让客户端存储的token无效，
                //1. 数据库中存储每次颁发的token，在aop中每次进行查询数据库来比较
                //   或者利用缓存来避免每次到数据库中查询token
                //   暂时不实现
                if(result != 1){
                    responseModel.setResponseCode(ResponseCode.NO_SUCH_USER.getCode());
                    responseModel.setMessage(ResponseCode.NO_SUCH_USER.getMessage());
                }
            }
        }

        return responseModel;
    }
}
