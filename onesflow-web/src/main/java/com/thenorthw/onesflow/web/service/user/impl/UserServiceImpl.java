package com.thenorthw.onesflow.web.service.user.impl;

import com.thenorthw.onesflow.common.dao.user.UserDao;
import com.thenorthw.onesflow.common.enums.MailType;
import com.thenorthw.onesflow.common.enums.RoleType;
import com.thenorthw.onesflow.common.model.mail.MailRecord;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.ShortUUIDUtil;
import com.thenorthw.onesflow.common.utils.mail.OnesflowMailClient;
import com.thenorthw.onesflow.web.service.mail.MailService;
import com.thenorthw.onesflow.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
public class UserServiceImpl implements UserService{
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
    public User getUserByLoginName(String loginname) {
        return userDao.getUserProfileByLoginName(loginname);
    }

    @Override
    public int initUserInfo(Long id) {
        //在博客模块下放入默认分类
        return 0;
    }
}
