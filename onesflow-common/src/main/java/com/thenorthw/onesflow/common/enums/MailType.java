package com.thenorthw.onesflow.common.enums;

/**
 * Created by theNorthW on 14/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public enum MailType {
    ACTIVATE_MAIL(1,"activate_mail"),
    RESETP_MAIL(2,"resetp mail");

    private String name;
    public int type;

    MailType(int t,String name){
        this.name = name;
        this.type = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
