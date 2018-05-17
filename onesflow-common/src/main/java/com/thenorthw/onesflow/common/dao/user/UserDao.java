package com.thenorthw.onesflow.common.dao.user;

import com.thenorthw.onesflow.common.model.user.LoginRecord;
import com.thenorthw.onesflow.common.model.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by theNorthW on 03/05/2017.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Repository
public interface UserDao {
    public int registerNewUser(User user);

    public User getUserProfileByLoginName(String loginname);
    public User getDetailedUserInfoByUid(Long id);

    public User getUserWithLAndP(@Param("loginname") String loginname,@Param("password") String password);
    public int addLoginRecord(LoginRecord loginRecord);

    //activate
    public int activateUser(@Param("id") Long id, @Param("gmtActivate") Date time);


    //info
    public int updateUserInfo(User user);

    //password
    public int changePassword(@Param("id")Long id,@Param("oldpass")String oldpass,@Param("newpass")String newpass);
    public int changePasswordDirectly(@Param("id")Long id,@Param("newpass")String newpass);


//
//    public User getUserProfileByUserId(long userId);
//
//    public List<User> getUserProfileByUserIds(@Param("userIds") List<String> userIds);
//
//    public int updateUserProfile(User user);
//
//    public int updateUserAvatar(@Param("userId") long userId, @Param("avatar") String avatar);
}
