package com.thenorthw.onesflow.common.dao.blog.tag;

import com.thenorthw.onesflow.common.model.blog.tag.BlogRArticleTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 20/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogRArticleTagDao {

	public int addR(BlogRArticleTag blogRArticleTag);

	public int deleteRByArticleIdAndTid(@Param("aid") Long aid, @Param("tid") Long tid);

	public List<BlogRArticleTag> getAllRs();

	public List<BlogRArticleTag> getRsByTagId(Long id);

	public List<BlogRArticleTag> getTagIdByArticleId(Long id);

}
