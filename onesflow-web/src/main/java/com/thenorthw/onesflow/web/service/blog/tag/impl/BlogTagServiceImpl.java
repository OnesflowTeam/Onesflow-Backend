package com.thenorthw.onesflow.web.service.blog.tag.impl;

import com.thenorthw.onesflow.common.dao.blog.tag.BlogTagDao;
import com.thenorthw.onesflow.common.dao.blog.tag.BlogRArticleTagDao;
import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import com.thenorthw.onesflow.common.model.blog.tag.BlogRArticleTag;
import com.thenorthw.onesflow.web.service.blog.tag.BlogTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
@Service
@Transactional
public class BlogTagServiceImpl implements BlogTagService {
	private static Logger logger = LoggerFactory.getLogger(BlogTagServiceImpl.class);


	@Autowired
	BlogTagDao blogTagDao;
	@Autowired
	BlogRArticleTagDao articleTagDao;


	@Override
	public List<BlogTag> getAllTags(Long id) {
		return blogTagDao.getAllTag(id);
	}

	@Override
	public BlogTag getTagById(Long id, Long gid) {
		return blogTagDao.getTagById(id,gid);
	}
	public List<BlogTag> getTagByIds(Long id, List<Long> tags) {
		return null;
	}

	@Override
	public BlogTag getTagByEn(Long id, String en) {
		return blogTagDao.getTagByEn(id,en);
	}

	@Override
	public List<BlogRArticleTag> getTagIdByArticleId(Long id) {
		return articleTagDao.getTagIdByArticleId(id);
	}

	@Override
	public int addTag(BlogTag blogTag) {
		//不允许重名
		if(blogTagDao.getTagByName(blogTag.getUid(),blogTag.getName()) != null){
			return -4;
		}

		if(blogTagDao.getTagByEn(blogTag.getUid(),blogTag.getEn()) != null){
			return -5;
		}

		return blogTagDao.addTag(blogTag);
	}

	@Override
	public int updateTag( Long uid,BlogTag articleBlogTag) {
		//首先判断这个tag是否属于user
		return 0;
	}

	@Override
	public int deleteTag(Long id, Long gid) {
		return 0;
	}

	@Override
	public List<BlogRArticleTag> getArticlesInTag(Long id, Long tagId) {
		return articleTagDao.getRsByTagId(tagId);
	}

	/**
	 * 由于一个article能和多个tag进行link，所以一般是先要寻找然后进行更新
	 * 需要判断article和tag是否在一个用户下
	 * @param articleId
	 * @return -2 - tag不存在
	 */
	public int linkArticleAndTag(Long articleId, List<Long> ids){
		//判断tag是否存在已经在前面步骤就已经判断过
		List<BlogRArticleTag> rbtl = getTagIdByArticleId(articleId);
		List<Long> tagsInArticle = new ArrayList<>(rbtl.size());
		for(BlogRArticleTag brat : rbtl){
			tagsInArticle.add(brat.getTagId());
		}

		//判断是否需要更新
		if(rbtl != null && tagsInArticle.equals(ids)){
			return 1;
		}

		//先来一波删除，再来一波添加
		for(Long tid : tagsInArticle){
			if(ids.contains(tid)){
				continue;
			}
			//如果当前文章里的tags没有在新加的ids里，删删删
			articleTagDao.deleteRByArticleIdAndTid(articleId,tid);
		}

		//再进行添加
		for(Long tid : ids){
			if(tagsInArticle.contains(tid)){
				continue;
			}
			BlogRArticleTag blogRArticleTag = new BlogRArticleTag(articleId,tid);
			Date now = new Date();
			blogRArticleTag.setGmtCreate(now);
			blogRArticleTag.setGmtModified(now);
			articleTagDao.addR(blogRArticleTag);
		}

		return 1;
	}

	public List<BlogRArticleTag> getArticlesInTag(Long tagId) {
		return articleTagDao.getRsByTagId(tagId);
	}

	//默认添加 '默认分类'
	public void initBlogWhenActivateUser(Long uid){
		BlogTag blogTag = new BlogTag();
		blogTag.setUid(uid);
		blogTag.setName("默认分类");
		blogTag.setEn("default");
		blogTagDao.addTag(blogTag);
	}


}
