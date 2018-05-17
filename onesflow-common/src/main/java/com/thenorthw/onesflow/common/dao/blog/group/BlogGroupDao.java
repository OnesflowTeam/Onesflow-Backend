package com.thenorthw.onesflow.common.dao.blog.group;

import com.thenorthw.onesflow.common.model.blog.group.BlogGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
@Repository
public interface BlogGroupDao {
	/**
	 * 获取所有group
	 * @return
	 */
	public List<BlogGroup> getAllGroup(Long uid);

	/**
	 * 防止重名
	 * @param name
	 * @return
	 */
	public BlogGroup getGroupByName(@Param("uid") Long id, @Param("name") String name);
	public BlogGroup getGroupByEn(@Param("uid") Long id, @Param("en") String en);

	public BlogGroup getGroupById(@Param("uid")Long uid,@Param("gid") Long gid);
	/**
	 * 该方法可用于增加article blogGroup
	 * @param blogGroup
	 * @return
	 */
	public int addGroup(BlogGroup blogGroup);

	/**
	 * 该方法可用于更新某个article blogGroup
	 * @param blogGroup
	 * @return
	 */
	public int updateGroup(BlogGroup blogGroup);

	/**
	 * 删除某个article group
	 * @param id
	 * @return
	 */
	public int deleteGroup(Long id);
}
