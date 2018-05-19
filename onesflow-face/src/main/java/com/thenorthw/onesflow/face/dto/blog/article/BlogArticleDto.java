package com.thenorthw.onesflow.face.dto.blog.article;


import com.thenorthw.onesflow.common.model.blog.article.BlogArticle;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticleContent;

import java.util.Date;
import java.util.List;

/**
 * @autuor theNorthW
 * @date 05/10/2017.
 * blog: theNorthW.net
 */

/**
 * 将数据库中的article和content放在一个对象中
 */
public class BlogArticleDto {
	Long id;
	String name;
	Long creator;
	Integer view;
	Integer grammar;
	Date gmtCreate;
	Date gmtModified;

	Integer length;
	String content;

	Long group;
	List<Long> tags;

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
		this.tags = tags;
	}

	public BlogArticleDto(BlogArticle a1, BlogArticleContent a2){
		this.id = a1.getId();
		this.name = a1.getName();
		this.creator = a1.getCreator();
		this.view = a1.getView();
		this.grammar = a1.getGrammar();
		this.gmtCreate = a1.getGmtCreate();
		this.gmtModified = a1.getGmtModified();

		if(a2 != null) {
			this.content = a2.getContent();
			this.length = a2.getLength();
		}
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
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

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public Integer getGrammar() {
		return grammar;
	}

	public void setGrammar(Integer grammar) {
		this.grammar = grammar;
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
}
