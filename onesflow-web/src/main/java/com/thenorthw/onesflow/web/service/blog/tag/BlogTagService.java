package com.thenorthw.onesflow.web.service.blog.tag;

import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import com.thenorthw.onesflow.common.model.blog.tag.BlogRArticleTag;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
public interface BlogTagService {

	/**
	 * 获取所有的article分类
	 * 在实现中会有 缓存 实例
	 * @return
	 */
	public List<BlogTag> getAllTags(Long id);


	/**
	 * 根据id获取相应article tag
	 * @param id
	 * @return
	 */
	public BlogTag getTagById(Long id, Long gid);
	public List<BlogTag> getTagByIds(Long id, List<Long> tags);
	public BlogTag getTagByEn(Long uid, String en);


	/**
	 * 根据articleId获取tagId
	 */
	public List<BlogRArticleTag> getTagIdByArticleId(Long id);

	/**
	 * 增加新的article tag
	 * @return
	 */
	public int addTag(BlogTag articleBlogTag);

	/**
	 * 更新article tag
	 * @return
	 */
	public int updateTag(Long uid, BlogTag articleBlogTag);

	/**
	 * 首先删除article 和 该tag的map映射
	 * 再删除这个tag
	 * @param id
	 * @return
	 */
	public int deleteTag(Long id, Long gid);


	public int linkArticleAndTag(Long articleId, List<Long> ids);

	public List<BlogRArticleTag> getArticlesInTag(Long id, Long tagId);

	public void initBlogWhenActivateUser(Long uid);
}
