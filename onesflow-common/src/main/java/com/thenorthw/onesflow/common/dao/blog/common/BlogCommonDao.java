package com.thenorthw.onesflow.common.dao.blog.common;

import com.thenorthw.onesflow.common.model.blog.common.BlogCommon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Repository
public interface BlogCommonDao {
    public int addBlogIntro(@Param("uid") Long uid, @Param("intro") String intro);
    public int updateBlogIntro(@Param("uid") Long uid, @Param("intro") String intro);
    public BlogCommon getBlogCommon(@Param("uid") Long uid);
}
