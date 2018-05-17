package com.thenorthw.onesflow.face.form.blog.group;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
public class GroupPostForm {
	@NotNull
	String name;

	@NotNull
	String en;


	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
