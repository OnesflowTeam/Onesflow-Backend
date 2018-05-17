package com.thenorthw.onesflow.common.dao.blog.group;

import com.thenorthw.onesflow.common.model.blog.group.BlogRArticleGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 20/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogRArticleGroupDao {

	public int addR(BlogRArticleGroup blogRArticleGroup);

	public int deleteRByArticleId(Long id);

	public List<BlogRArticleGroup> getAllRs();

	public List<BlogRArticleGroup> getRsByGroupId(Long id);

	public BlogRArticleGroup getGroupIdByArticleId(Long id);

//	public List<BlogRArticleGroup> getRsByArticleId(Long id);
}
