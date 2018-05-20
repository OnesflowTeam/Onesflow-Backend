package com.thenorthw.onesflow.web.service.blog.common;

import com.thenorthw.onesflow.common.model.blog.common.BlogCommon;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public interface BlogCommonService {
    public int addBlogIntroWhenActivate(Long uid,String intro);
    public int updateBlogIntro(Long uid,String intro);

    public String getBlogIntro(Long uid);
}
