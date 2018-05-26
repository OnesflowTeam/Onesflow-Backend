package com.thenorthw.onesflow.common.dao.blog.comment;

import com.thenorthw.onesflow.common.model.blog.comment.BlogComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by theNorthW on 22/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Repository
public interface BlogCommentDao {
    public int addBlogArticleComment(BlogComment blogComment);

    public List<BlogComment> getBlogCommentByPage(@Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize,@Param("aid")Long aid);
}