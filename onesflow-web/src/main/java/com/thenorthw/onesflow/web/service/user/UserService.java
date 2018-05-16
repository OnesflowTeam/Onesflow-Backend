package com.thenorthw.onesflow.web.service.user;

import com.thenorthw.onesflow.common.model.user.User;


/**
 * Created by theNorthW on 03/05/2017.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public interface UserService {

    public int register(User user);

    public int activateUser(Long id);

    //login
    public User userLoginByLP(String loginname,String password);
    public User userLoginByToken(String token);


    //get info
    public User getUserByLoginName(String loginname);
    //用户自己获取自己信息
    public User getDetailedUserInfoByUid(Long uid);
    //update
    public int updateUserInfo(User user);


    //password
    public int changePassword(Long id, String oldpass, String newpass);



    //init user info
    public int initUserInfo(Long id);


}
