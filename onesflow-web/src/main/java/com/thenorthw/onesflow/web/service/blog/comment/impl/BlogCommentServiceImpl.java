package com.thenorthw.onesflow.web.service.blog.comment.impl;

import com.thenorthw.onesflow.common.dao.blog.comment.BlogCommentDao;
import com.thenorthw.onesflow.common.model.blog.comment.BlogComment;
import com.thenorthw.onesflow.web.service.blog.comment.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by theNorthW on 22/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
@Transactional
public class BlogCommentServiceImpl implements BlogCommentService{
    @Autowired
    BlogCommentDao blogCommentDao;


    @Override
    public int addBlogComment(BlogComment blogComment) {
        return blogCommentDao.addBlogArticleComment(blogComment);
    }
}
