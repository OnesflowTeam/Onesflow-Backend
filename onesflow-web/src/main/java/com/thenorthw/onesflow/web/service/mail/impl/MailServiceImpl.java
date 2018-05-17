package com.thenorthw.onesflow.web.service.mail.impl;

import com.thenorthw.onesflow.common.dao.mail.MailDao;
import com.thenorthw.onesflow.common.enums.MailType;
import com.thenorthw.onesflow.common.model.mail.MailRecord;
import com.thenorthw.onesflow.common.model.user.User;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.mail.OnesflowMailClient;
import com.thenorthw.onesflow.web.service.mail.MailService;
import com.thenorthw.onesflow.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by theNorthW on 14/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
public class MailServiceImpl implements MailService{
    @Autowired
    MailDao mailDao;
    @Autowired
    UserService userService;

    @Override
    public int sendActivateMailAfterRegister(User user, String token){
        if(sendActivateMail(user,10) == 1){
            return 1;
        }
        //发送失败
        return 0;
    }

    @Override
    public int sendActivateMailAgain(String email) {
        //为了防止恶意发送，需要寻找上一次给该用户发送邮件的记录，并且间隔5min
        //首先查找是否有该用户
        User user = userService.getUserByLoginName(email);
        if(user != null){
            //如果已经激活则不再发送
            if(user.getGmtActivate() != null){
                return -3;
            }
            //查找上一次发送记录
            MailRecord mailRecord = mailDao.getMailRecordByToUserAndType(user.getId(),MailType.ACTIVATE_MAIL.getType());
            if(mailRecord == null){
                //直接发送
                if(sendActivateMail(user,10) == 1){
                    return 1;
                }
            }else {
                if(mailRecord.getGmtCreate().getTime() + 300000 < (new Date()).getTime()){
                    //已超过间隔时间，可以发送
                    if(sendActivateMail(user,10) == 1){
                        return 1;
                    }
                }else{
                    return -2;
                }
            }
        }else{
            //没有该用户
            return -1;
        }
        return 0;
    }



    @Override
    public int sendResetpMail(String email) {
        //为了防止恶意发送，需要寻找上一次给该用户发送邮件的记录，并且间隔5min
        //首先查找是否有该用户
        User user = userService.getUserByLoginName(email);
        if(user != null){
            //如果已经激活则不允许发送
            if(user.getGmtActivate() == null){
                return -3;
            }
            //查找上一次发送记录
            MailRecord mailRecord = mailDao.getMailRecordByToUserAndType(user.getId(),MailType.RESETP_MAIL.getType());
            if(mailRecord == null){
                //直接发送
                if(sendResetpMail(user,10) == 1){
                    return 1;
                }
            }else {
                if(mailRecord.getGmtCreate().getTime() + 300000 < (new Date()).getTime()){
                    //已超过间隔时间，可以发送
                    if(sendResetpMail(user,10) == 1){
                        return 1;
                    }
                }else{
                    return -2;
                }
            }
        }else{
            //没有该用户
            return -1;
        }
        return 0;
    }

    private int sendActivateMail(User user,int expire){
        String token = JwtUtil.createActivateToken(user.getId()+"",expire);
        //直接发送
        if(OnesflowMailClient.sendMail(user.getLoginname(), token,10,MailType.ACTIVATE_MAIL) == 1) {
            //增加记录
            MailRecord mailRecord = new MailRecord();
            Date now = new Date();
            mailRecord.setToUser(user.getId());
            mailRecord.setType(MailType.ACTIVATE_MAIL.getType());
            mailRecord.setContent(token);
            mailRecord.setGmtCreate(now);
            mailRecord.setGmtModified(now);

            mailDao.addMailRecord(mailRecord);
            //发送成功
            return 1;
        }
        return 0;
    }

    private int sendResetpMail(User user,int expire){
        String token = JwtUtil.createResetpToken(user.getId()+"",expire);
        //直接发送
        if(OnesflowMailClient.sendMail(user.getLoginname(), token,10,MailType.RESETP_MAIL) == 1) {
            //增加记录
            MailRecord mailRecord = new MailRecord();
            Date now = new Date();
            mailRecord.setToUser(user.getId());
            mailRecord.setType(MailType.RESETP_MAIL.getType());
            mailRecord.setContent(token);
            mailRecord.setGmtCreate(now);
            mailRecord.setGmtModified(now);

            mailDao.addMailRecord(mailRecord);
            //发送成功
            return 1;
        }
        return 0;
    }
}
