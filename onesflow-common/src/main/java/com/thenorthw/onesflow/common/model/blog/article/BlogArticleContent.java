package com.thenorthw.onesflow.common.model.blog.article;

import java.util.Date;

/**
 * @autuor theNorthW
 * @date 20/09/2017.
 * blog: thenorthw.com
 */
public class BlogArticleContent {
	Long id;
	Integer length;
	String content;
	Date gmtCreate;
	Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
