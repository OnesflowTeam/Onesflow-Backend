package com.thenorthw.onesflow.web.service.blog.group.impl;

import com.thenorthw.onesflow.common.dao.blog.group.BlogGroupDao;
import com.thenorthw.onesflow.common.dao.blog.group.BlogRArticleGroupDao;
import com.thenorthw.onesflow.common.model.blog.group.BlogGroup;
import com.thenorthw.onesflow.common.model.blog.group.BlogRArticleGroup;
import com.thenorthw.onesflow.web.service.blog.group.BlogGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
@Service
@Transactional
public class BlogGroupServiceImpl implements BlogGroupService {
	private static Logger logger = LoggerFactory.getLogger(BlogGroupServiceImpl.class);


	@Autowired
	BlogGroupDao blogGroupDao;
	@Autowired
	BlogRArticleGroupDao articleGroupDao;


	@Override
	public List<BlogGroup> getAllGroups(Long id) {
		return blogGroupDao.getAllGroup(id);
	}

	@Override
	public BlogGroup getGroupById(Long id, Long gid) {
		return blogGroupDao.getGroupById(id,gid);
	}

	@Override
	public BlogGroup getGroupByEn(Long id, String en) {
		return blogGroupDao.getGroupByEn(id,en);
	}

	@Override
	public BlogRArticleGroup getGroupIdByArticleId(Long id) {
		return articleGroupDao.getGroupIdByArticleId(id);
	}

	@Override
	public int addGroup(BlogGroup blogGroup) {
		//不允许重名
		if(blogGroupDao.getGroupByName(blogGroup.getUid(),blogGroup.getName()) != null){
			return -4;
		}

		if(blogGroupDao.getGroupByEn(blogGroup.getUid(),blogGroup.getEn()) != null){
			return -5;
		}

		return blogGroupDao.addGroup(blogGroup);
	}

	@Override
	public int updateGroup( Long uid,BlogGroup articleBlogGroup) {
		//首先判断这个group是否属于user
		return 0;
	}

	@Override
	public int deleteGroup(Long id, Long gid) {
		return 0;
	}

	@Override
	public List<BlogRArticleGroup> getArticlesInGroup(Long id, Long groupId) {
		return articleGroupDao.getRsByGroupId(groupId);
	}

	/**
	 * 由于一个article只能和一个group进行link，所以一般是先要寻找然后进行更新
	 * 需要判断article和group是否在一个用户下
	 * @param articleId
	 * @param groupId
	 * @return -2 - group不存在
	 */
	public int linkArticleAndGroup(Long articleId, Long groupId){
		//判断group是否存在已经在前面步骤就已经判断过
		BlogRArticleGroup blogRArticleGroup1 = getGroupIdByArticleId(articleId);

		//判断是否需要更新
		if(blogRArticleGroup1 != null && blogRArticleGroup1.getGroupId().equals(groupId)){
			return 1;
		}

		articleGroupDao.deleteRByArticleId(articleId);
		BlogRArticleGroup blogRArticleGroup = new BlogRArticleGroup(articleId,groupId);
		Date now = new Date();
		blogRArticleGroup.setGmtCreate(now);
		blogRArticleGroup.setGmtModified(now);
		articleGroupDao.addR(blogRArticleGroup);

		return 1;
	}

	public List<BlogRArticleGroup> getArticlesInGroup(Long groupId) {
		return articleGroupDao.getRsByGroupId(groupId);
	}

	//默认添加 '默认分类'
	public void initBlogWhenActivateUser(Long uid){
		BlogGroup blogGroup = new BlogGroup();
		blogGroup.setUid(uid);
		blogGroup.setName("默认分类");
		blogGroup.setEn("default");
		blogGroupDao.addGroup(blogGroup);
	}
}
