package com.thenorthw.onesflow.web.service.blog.article;

import com.thenorthw.onesflow.common.model.blog.article.BlogArticle;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticleContent;
import com.thenorthw.onesflow.face.dto.blog.article.BlogArticleDto;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 17/09/2017.
 * blog: thenorthw.com
 */
public interface BlogArticleService {
	public BlogArticle getArticleInfo(Long aid);

	public BlogArticleContent getArticleContent(Long aid);

	public List<BlogArticleDto> getRecentArticles(Long uid);

	public List<BlogArticleDto> getAllArticles(Long uid, int pageNumber, int pageSize);
	public List<BlogArticle> getArticlesByIds(List<Long> ids);

	public List<BlogArticleContent> getArticleContents(List<Long> ids);

	public BlogArticleDto getArticleDto(Long id, boolean edit);

	public int getTotalArticleNumber(Long uid);

	/**
	 * @param blogArticle
	 * @param blogArticleContent
	 * @return 返回刚插入的article id
	 */
	public BlogArticleDto postArticle(BlogArticle blogArticle, BlogArticleContent blogArticleContent, Long groupId);

	public int deleteArticle(Long id, Long accountId);

	public int updateArticle(BlogArticle blogArticle, BlogArticleContent blogArticleContent, Long postId,Long groupId);
}
