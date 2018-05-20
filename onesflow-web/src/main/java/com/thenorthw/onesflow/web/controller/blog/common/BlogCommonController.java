package com.thenorthw.onesflow.web.controller.blog.common;

import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.face.form.blog.common.BlogAboutMeForm;
import com.thenorthw.onesflow.web.service.blog.common.BlogCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by theNorthW on 20/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Controller
@RequestMapping(value = "/web/v1")
public class BlogCommonController {
    @Autowired
    HttpServletRequest httpServletRequest;


    @Autowired
    BlogCommonService blogCommonService;

    @RequestMapping(value = "/blog/u/{id}/aboutme",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getBlogIntro(@PathVariable String id){
        ResponseModel responseModel = new ResponseModel();

        Long uid = Long.parseLong(id);
        responseModel.setData(blogCommonService.getBlogIntro(uid));
        return responseModel;
    }

    @RequestMapping(value = "/blog/aboutme",method = RequestMethod.POST)
    @ResponseBody
    @LoginNeed
    public ResponseModel updateBlogIntro(@Valid BlogAboutMeForm blogAboutMeForm , BindingResult bindingResult){
        ResponseModel responseModel = new ResponseModel();

        Long uid = JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER)));
        blogCommonService.updateBlogIntro(uid,blogAboutMeForm.getAboutMe());
        return responseModel;
    }
}
