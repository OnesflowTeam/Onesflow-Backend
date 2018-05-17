package com.thenorthw.onesflow.face.form.blog.group;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
public class GroupUpdateForm {
	@NotNull
	Long id;

	@NotNull
	@Size(min = 1, max = 10)
	String name;

	@NotNull
	String en;


	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
