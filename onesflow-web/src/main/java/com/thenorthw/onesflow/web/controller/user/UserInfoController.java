package com.thenorthw.onesflow.web.controller.user;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.user.UserInfoUpdateForm;
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

/**
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class UserInfoController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @Autowired
    UserService userService;


    @RequestMapping(value = "/user/info",method = RequestMethod.POST)
    @ResponseBody
    @LoginNeed
    public ResponseModel login(@Valid UserInfoUpdateForm userInfoUpdateForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        String token = httpServletRequest.getHeader("x-token");

        Long uid = JwtUtil.getUidFromClaims(JwtUtil.verify(token));

        User user = new User();
        user.setId(uid);
        user.setNick(userInfoUpdateForm.getNick());
        user.setIntroduction(userInfoUpdateForm.getIntroduction());
        user.setSex(Integer.parseInt(userInfoUpdateForm.getSex()));

        int result = userService.updateUserInfo(user);

        if(result != 1){
            responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        return responseModel;
    }
}
