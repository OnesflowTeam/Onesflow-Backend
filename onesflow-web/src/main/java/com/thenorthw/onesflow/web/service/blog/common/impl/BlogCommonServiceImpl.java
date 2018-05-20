package com.thenorthw.onesflow.web.service.blog.common.impl;

import com.thenorthw.onesflow.common.dao.blog.common.BlogCommonDao;
import com.thenorthw.onesflow.web.service.blog.common.BlogCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
@Transactional
public class BlogCommonServiceImpl implements BlogCommonService {
    @Autowired
    BlogCommonDao blogCommonDao;
    @Override
    public int updateBlogIntro(Long uid, String intro) {
        return blogCommonDao.updateBlogIntro(uid,intro);
    }

    @Override
    public int addBlogIntroWhenActivate(Long uid, String intro) {
        return blogCommonDao.addBlogIntro(uid,intro);
    }

    @Override
    public String getBlogIntro(Long uid) {
        return blogCommonDao.getBlogCommon(uid).getBlogIntro();
    }
}
