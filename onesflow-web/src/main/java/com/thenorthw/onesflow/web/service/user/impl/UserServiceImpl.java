package com.thenorthw.onesflow.web.service.user.impl;

import com.auth0.jwt.interfaces.Claim;
import com.thenorthw.onesflow.common.dao.user.UserDao;
import com.thenorthw.onesflow.common.model.user.LoginRecord;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.web.service.mail.MailService;
import com.thenorthw.onesflow.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    UserDao userDao;

    @Autowired
    MailService mailService;

    @Override
    public int register(User user) {
        //首先判断是否有同名用户
        User sameNameUser = userDao.getUserProfileByLoginName(user.getLoginname());

        if(sameNameUser != null){
            return -1;
        }

        int result = userDao.registerNewUser(user);

        if(result == 1){
            //根据返回id判断是否注册成功
            //注册成功即发送激活邮件
            //生成activateToken，使用jwt,jwt中加入user_id
            String token = JwtUtil.createActivateToken(""+user.getId(),10);
            if(0 == mailService.sendActivateMailAfterRegister(user,token)){
                return -2;
            }
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int activateUser(Long id) {
        return userDao.activateUser(id,new Date());
    }

    @Override
    public User userLoginByLP(String loginname, String password) {
        User user = userDao.getUserProfileByLoginName(loginname);


        if(user == null){
            return null;
        }else if(user.getPassword().equals(password)){
            //插入登录记录
            insertLoginRecord(user,1);

            //将这两行不对外显示
            user.setPassword(null);
            user.setLoginname(null);
        }else {
            //插入登录失败记录
            insertLoginRecord(user,0);
            return null;
        }

        return user;
    }

    @Override
    public User userLoginByToken(String token) {
        Map<String,Claim> cs = JwtUtil.verify(token);

        if(token == null || cs == null){
            insertLoginRecord(null,0);
            return null;
        }

        if(JwtUtil.getUidFromClaims(cs) == null){
            insertLoginRecord(null,0);
            return null;
        }

        User user = getDetailedUserInfoByUid(JwtUtil.getUidFromClaims(cs));
        insertLoginRecord(user,1);

        return user;
    }

    @Override
    public User getUserByLoginName(String loginname) {
        return userDao.getUserProfileByLoginName(loginname);
    }

    @Override
    public User getDetailedUserInfoByUid(Long uid) {
        return userDao.getDetailedUserInfoByUid(uid);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public int changePassword(Long id, String oldpass, String newpass) {
        return userDao.changePassword(id,oldpass,newpass);
    }

    @Override
    public int initUserInfo(Long id) {
        //在博客模块下放入默认分类
        return 0;
    }


    //插入登录记录
    private void insertLoginRecord(User user,Integer success){
        String fromSource = "X-Real-IP";
        String ip = httpServletRequest.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("X-Forwarded-For");
            fromSource = "X-Forwarded-For";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
            fromSource = "Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
            fromSource = "WL-Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
            fromSource = "httpServletRequest.getRemoteAddr";
        }
        LoginRecord loginRecord = new LoginRecord();
        Date now = new Date();
        loginRecord.setUid(user == null ? 4444444444444444444L : user.getId());
        loginRecord.setAddress(ip+"|"+fromSource);
        loginRecord.setGmtCreate(now);
        loginRecord.setGmtModified(now);
        loginRecord.setSuccess(success);
        userDao.addLoginRecord(loginRecord);
    }

}
