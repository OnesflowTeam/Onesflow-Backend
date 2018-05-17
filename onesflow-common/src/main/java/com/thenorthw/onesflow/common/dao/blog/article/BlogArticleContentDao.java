package com.thenorthw.onesflow.common.dao.blog.article;

import com.thenorthw.onesflow.common.model.blog.article.BlogArticleContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 23/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogArticleContentDao {
	public BlogArticleContent getArticleContent(Long id);

	public List<BlogArticleContent> getArticleContentsByIds(@Param("ids") List<Long> ids);

	public int postArticleContent(BlogArticleContent blogArticleContent);

	public int updateArticleContent(BlogArticleContent blogArticleContent);

	public int deleteArticleContent(Long id);
}
