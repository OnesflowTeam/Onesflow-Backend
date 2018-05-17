package com.thenorthw.onesflow.web.service.blog.group;

import com.thenorthw.onesflow.common.model.blog.group.BlogGroup;
import com.thenorthw.onesflow.common.model.blog.group.BlogRArticleGroup;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
public interface BlogGroupService {

	/**
	 * 获取所有的article分类
	 * 在实现中会有 缓存 实例
	 * @return
	 */
	public List<BlogGroup> getAllGroups(Long id);


	/**
	 * 根据id获取相应article group
	 * @param id
	 * @return
	 */
	public BlogGroup getGroupById(Long id, Long gid);
	public BlogGroup getGroupByEn(Long id, String en);


	/**
	 * 根据articleId获取groupId
	 */
	public BlogRArticleGroup getGroupIdByArticleId(Long id);

	/**
	 * 增加新的article group
	 * @return
	 */
	public int addGroup(BlogGroup articleBlogGroup);

	/**
	 * 更新article group
	 * @return
	 */
	public int updateGroup(Long uid,BlogGroup articleBlogGroup);

	/**
	 * 首先删除article 和 该group的map映射
	 * 再删除这个group
	 * @param id
	 * @return
	 */
	public int deleteGroup(Long id,Long gid);


	public int linkArticleAndGroup(Long articleId, Long groupId);

	public List<BlogRArticleGroup> getArticlesInGroup(Long id, Long groupId);

	public void initBlogWhenActivateUser(Long uid);
}
