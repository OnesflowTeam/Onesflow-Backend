package com.thenorthw.onesflow.web.controller.user;

import com.auth0.jwt.interfaces.Claim;
import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.enums.RoleType;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.ShortUUIDUtil;
import com.thenorthw.onesflow.face.form.user.UserActivateForm;
import com.thenorthw.onesflow.face.form.user.UserRegisterForm;
import com.thenorthw.onesflow.web.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * Created by theNorthW on 2018/5/14.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping("/web/v1")
public class RegisterController {
    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @Autowired
    UserService userService;

    /**
     * 该接口为了方便微信用户首次使用时进行
     * @return 具体返回结果请到API文档中查看
     */
    @RequestMapping(value = "/user/joinus",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel joinUs(@Valid UserRegisterForm createForm, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        //如果创建成功,返回创建好的user记录的user_id

        //判断注册类型是普通用户还是店铺 - 注册类型默认为false
        User user = new User();
        Date now = new Date();

        user.setLoginname(createForm.getLoginname());
        user.setPassword(createForm.getPassword());
        user.setGmtCreate(now);
        user.setGmtModified(now);
        user.setSex(3);
        user.setIntroduction("没看到有介绍，再等等？");
        user.setNick("用户"+ ShortUUIDUtil.randomUUID().substring(0,8));
        user.setRoleId(RoleType.NORMAL_USER.roleId);

        int result = userService.register(user);

        if (result == 0) {
            responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
            responseModel.setMessage("Error:create account error.");
        } else if (result == -1) {
            responseModel.setResponseCode(ResponseCode.EXISTED_USER.getCode());
            responseModel.setMessage("Error:existed user.");
        } else if (result == -2) {
            responseModel.setResponseCode(ResponseCode.ACTIVATE_MAIL_SEND_FAIL.getCode());
            responseModel.setMessage(ResponseCode.ACTIVATE_MAIL_SEND_FAIL.getMessage());
        } else {
            //注册成功，利用mail来发送邮件
            logger.info("成功创建用户，id:{}，email:{}",user.getId(),user.getLoginname());
            //初始化用户信息，比如在博客模块中放入默认分类
        }

        return responseModel;
    }

    @RequestMapping(value = "/user/activate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel activate(@Valid UserActivateForm userActivateForm,BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        Map<String,Claim> cs = JwtUtil.verifyActivate(userActivateForm.getActivateToken());

        if(cs == null){
            responseModel.setResponseCode(ResponseCode.ILLEGAL_ACTIVATE_TOKEN.getCode());
            responseModel.setMessage(ResponseCode.ILLEGAL_ACTIVATE_TOKEN.getMessage());
        }else {
            int result = userService.activateUser(JwtUtil.getUidFromClaims(cs));

            if(result != 1){
                responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
            }
        }

        return responseModel;
    }
}
