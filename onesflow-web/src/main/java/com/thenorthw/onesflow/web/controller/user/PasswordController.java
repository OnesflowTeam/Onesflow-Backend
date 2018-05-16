package com.thenorthw.onesflow.web.controller.user;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.user.UserChangePasswordForm;
import com.thenorthw.onesflow.face.form.user.UserInfoUpdateForm;
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

        String token = httpServletRequest.getHeader("x-token");

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
}
