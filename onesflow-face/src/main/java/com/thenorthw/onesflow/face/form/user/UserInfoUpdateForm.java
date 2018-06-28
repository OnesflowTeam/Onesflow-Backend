package com.thenorthw.onesflow.face.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @autuor theNorthW
 * @date 18/09/2017.
 * onesflow: thenorthw.com
 */
public class UserInfoUpdateForm {
	@Size(max = 36)
	@NotNull
	String nick;

	@Pattern(regexp = "1|2|3")
	@NotNull
	String sex;

	@Size(max = 188)
	@NotNull
	String introduction;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
