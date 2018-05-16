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


    //get info
    public User getUserByLoginName(String loginname);



    //init user info
    public int initUserInfo(Long id);


}
