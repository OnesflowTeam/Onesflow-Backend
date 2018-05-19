package com.thenorthw.onesflow.face.form.blog.group;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
public class GroupPostForm {
	@NotNull
	@Size(min = 1, max = 18)
	@Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]{1,18}$")
	String name;

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]{1,18}$")
	@Size(min = 1, max = 18)
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
