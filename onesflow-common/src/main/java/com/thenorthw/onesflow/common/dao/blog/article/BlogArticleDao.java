package com.thenorthw.onesflow.common.dao.blog.article;

import com.thenorthw.onesflow.common.model.blog.article.BlogArticle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 20/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogArticleDao {
	/**
	 * 按照最近时间顺序获取limit文章
	 * @param limit
	 * @return
	 */
	public List<BlogArticle> getRecentArticles(@Param("uid") Long uid,@Param("limit") int limit);

	public List<BlogArticle> getAllArticles(@Param("uid")Long uid, @Param("startNumber")Integer startNumber, @Param("pageSize")Integer pageSize);

	public BlogArticle getArticleById(Long id);

	public List<BlogArticle> getArticleByIds(List<Long> ids);

	public int updateArticleView(@Param("aid") Long aid);

	public int getTotalNumber(@Param("uid") Long id);

	/**
	 * 上传article基础信息，和postContent需要一起使用
	 * @param blogArticle
	 * @return
	 */
	public int postArticle(BlogArticle blogArticle);

	public int updateArticle(BlogArticle blogArticle);

	public int deleteArticle(Long id);

	public int updateViewTimes(Long id);
}
