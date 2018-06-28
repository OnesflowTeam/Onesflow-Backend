package com.thenorthw.onesflow.common.dao.blog.tag;

import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogTagDao {
	/**
	 * 获取所有tag
	 * @return
	 */
	public List<BlogTag> getAllTag(Long uid);

	/**
	 * 防止重名
	 * @param name
	 * @return
	 */
	public BlogTag getTagByName(@Param("uid") Long id, @Param("name") String name);
	public BlogTag getTagByEn(@Param("uid") Long id, @Param("en") String en);

	public BlogTag getTagById(@Param("uid") Long uid, @Param("gid") Long gid);
	/**
	 * 该方法可用于增加article blogTag
	 * @param blogTag
	 * @return
	 */
	public int addTag(BlogTag blogTag);

	/**
	 * 该方法可用于更新某个article blogTag
	 * @param blogTag
	 * @return
	 */
	public int updateTag(BlogTag blogTag);

	/**
	 * 删除某个article tag
	 * @param id
	 * @return
	 */
	public int deleteTag(Long id);
}
