package com.thenorthw.onesflow.common;

/**
 * Created by theNorthW on 06/04/2017.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public enum ResponseCode {
    OK(200,"ok"),



    BAD_REQUEST(400,"bad request"),
    UNAUTHORIZED(401,"unauthorized"),
    FORBIDDEN(403,"forbidden"),
    NOT_FOUND(404,"not found"),

    INTERNAL_SERVER_ERROR(500,"internal server error"),

    //parameter invalid
    PARAMETER_ERROR(999,"The http request parameters are wrong."),

    //register
    EXISTED_USER(1000,"The account has registered."),

    //mail
    ILLEGAL_ACTIVATE_TOKEN(1001,"Illegal activate token"),
    ILLEGAL_RESETP_TOKEN(1013,"Illegal resetp token"),
    ACTIVATE_MAIL_SEND_FAIL(1002,"Send activate mail fail"),
    ACTIVATE_MAIL_SEND_TOO_FREQUENTLY(1004,"Send activate mail too frequently"),
    RESETP_MAIL_SEND_TOO_FREQUENTLY(1009,"Send resetp mail too frequently"),
    NO_SUCH_USER(1003,"no such user"),
    ALREADY_ACTIVATED(1008,"Already activated."),

    //login
    NO_SUCH_ACCOUNT_OR_PASSWORD_WRONG(1005,"No such account exists or wrong password"),
    LOGIN_FAIL(1006,"Login error."),
    HAVE_NOT_ACTIVATED(1007,"Have not Activate"),

    //update user avatar info
    OSS_UPLOAD_ERROR(1011,"upload error"),

    //change password
    WRONG_OLD_PASSWORD(1012,"Wrong old password"),

    //group
//    NO_SUCH_PARENT_GROUP(1031,"no such parent group or group level out of 2."),
//    PARENT_LEVEL_ILLEGAL(1032,"only level 1&2 group is allowed."),
    GROUP_ADD_ERROR(1031,"group add error."),
    NO_SUCH_GROUP(1033,"group not exists."),
    GROUP_DELETE_ERROR(1034,"group delete error."),
    DUPLICATED_GROUP(1035,"duplicated chinese group."),
    DUPLICATED_GROUP_EN(1036,"duplicated english group."),


    //tag
    TAG_ADD_ERROR(1041,"tag add error."),
    TAG_DELETE_ERROR(1042,"tag delete error."),
    NO_SUCH_TAG(1043,"no such tag."),
    DUPLICATED_TAG(1045,"duplicated chinese group."),
    DUPLICATED_TAG_EN(1046,"duplicated english group."),

	//notification
	NO_SUCH_NOTIFICATION(1061,"no such notification");




    ResponseCode(int code,String m){
        this.code = code;
        this.message = m;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\""+code+"\"";
    }


}
