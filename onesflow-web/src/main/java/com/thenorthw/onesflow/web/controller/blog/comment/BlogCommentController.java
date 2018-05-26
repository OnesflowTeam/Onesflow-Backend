package com.thenorthw.onesflow.web.controller.blog.comment;

import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.blog.comment.BlogComment;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.blog.comment.BlogCommentPostForm;
import com.thenorthw.onesflow.web.service.blog.comment.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by theNorthW on 22/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping(value = "/web/v1")
public class BlogCommentController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    BlogCommentService blogCommentService;

    @RequestMapping(value = "/blog/comment", method = RequestMethod.POST)
    @ResponseBody
    @LoginNeed
    public ResponseModel addBlogComment(BlogCommentPostForm blogCommentPostForm, BindingResult bindingResult){
        ResponseModel responseModel = new ResponseModel();

        Long uid = JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER)));

        BlogComment blogComment = new BlogComment();
        blogComment.setAid(blogCommentPostForm.getAid());
        blogComment.setUid(uid);
        blogComment.setContent(blogCommentPostForm.getContent());
        blogCommentService.addBlogComment(blogComment);

        return responseModel;
    }


}
