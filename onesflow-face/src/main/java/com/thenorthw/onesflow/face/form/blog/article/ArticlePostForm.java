package com.thenorthw.onesflow.face.form.blog.article;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @autuor theNorthW
 * @date 23/09/2017.
 * blog: thenorthw.com
 */
public class ArticlePostForm {
	@Size(min = 1, max = 128)
	@NotNull
	String name;

	@NotNull
	String content;

	@Pattern(regexp = "0|1")
	@NotNull
	String grammar;

	@Pattern(regexp = "[1-9]\\d*")
	@NotNull
	String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGrammar() {
		return grammar;
	}

	public void setGrammar(String grammar) {
		this.grammar = grammar;
	}

}
