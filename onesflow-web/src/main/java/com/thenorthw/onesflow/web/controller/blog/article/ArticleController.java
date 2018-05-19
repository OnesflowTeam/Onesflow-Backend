package com.thenorthw.onesflow.web.controller.blog.article;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticle;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticleContent;
import com.thenorthw.onesflow.common.model.blog.group.BlogGroup;
import com.thenorthw.onesflow.common.model.blog.group.BlogRArticleGroup;
import com.thenorthw.onesflow.common.model.blog.tag.BlogRArticleTag;
import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.NumberAssert;
import com.thenorthw.onesflow.face.dto.blog.article.BlogArticleDto;
import com.thenorthw.onesflow.face.form.Page;
import com.thenorthw.onesflow.face.form.blog.article.ArticlePostForm;
import com.thenorthw.onesflow.face.form.blog.article.ArticleUpdateForm;
import com.thenorthw.onesflow.web.service.blog.article.BlogArticleService;
import com.thenorthw.onesflow.web.service.blog.group.BlogGroupService;
import com.thenorthw.onesflow.web.service.blog.tag.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @autuor theNorthW
 * @date 17/09/2017.
 * blog: thenorthw.com
 */
@RequestMapping(value = "/web/v1")
@Controller
public class ArticleController {
	@Autowired
	HttpServletRequest httpServletRequest;
	@Autowired
	BlogArticleService blogArticleService;
	@Autowired
	BlogGroupService blogGroupService;
	@Autowired
    BlogTagService blogTagService;

	/**
	 * 来获取最近发表的article 10篇
	 * @return
	 */
	@RequestMapping(value = "/blog/u/{id}/article/recent", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getRecentArticles(@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*") || !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		List<BlogArticleDto> dtos = blogArticleService.getRecentArticles(Long.parseLong(id));

		responseModel.setData(dtos);

		return responseModel;
	}

	/**
	 * 来获取所有发表过的articles，不过返回的只是不包含content的元信息
	 * 前端可以画一条时间线
	 * @return
	 */
	@RequestMapping(value = "/blog/u/{id}/article/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getAllArticles(@Valid Page page, @PathVariable String id,BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();
		if(!id.matches("^[1-9]\\d*") ||   !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		Long uid = Long.parseLong(id);
		List<BlogArticleDto> as = blogArticleService.getAllArticles(uid,Integer.valueOf(page.getPageNumber())-1,Integer.valueOf(page.getPageSize()));

		Map<String,Object> data = new HashMap<String, Object>();
		data.put("pageSize",page.getPageSize());
		data.put("pageNumber",page.getPageNumber());
		data.put("total", blogArticleService.getTotalArticleNumber(uid));
		data.put("articles",as);

		responseModel.setData(data);

		return responseModel;
	}

    /**
     * 此处不用返回很详细的信息，连tag都不用返回
     * @param groupName
     * @param id
     * @return
     */
	@RequestMapping(value = "/blog/u/{id}/groups/{groupName}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getArticleInGroup(@PathVariable String groupName,@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*")  || !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		if(!groupName.matches("^[A-Za-z0-9]{1,18}$")){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		Long uid = Long.parseLong(id);
		//首先在group表中找到groupId，然后到关系表中找到group下的文章
		BlogGroup blogGroup = blogGroupService.getGroupByEn(uid,groupName);
		if(blogGroup != null) {
			List<BlogRArticleGroup> rs = blogGroupService.getArticlesInGroup(uid,blogGroup.getId());

			List<Long> ids = new ArrayList<Long>();

			if(rs.size() == 0){
				return responseModel;
			}

			for(BlogRArticleGroup r : rs){
				ids.add(r.getArticleId());
			}

			List<BlogArticle> l1 = blogArticleService.getArticlesByIds(ids);
			List<BlogArticleDto> l2 = new ArrayList<BlogArticleDto>();

			for(BlogArticle a : l1){
				BlogArticleDto t = new BlogArticleDto(a,null);
				t.setGroup(blogGroup.getId());
				l2.add(t);
			}

			responseModel.setData(l2);
		}else {
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
		}

		return responseModel;
	}

    /**
     * 此处不用返回很详细的信息，连group都不用返回
     * @param tagName
     * @param id
     * @return
     */
	@RequestMapping(value = "/blog/u/{id}/tags/{tagName}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getArticleInTag(@PathVariable String tagName,@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*")  || !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		if(!tagName.matches("^[A-Za-z0-9]{1,18}$")){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		Long uid = Long.parseLong(id);
		//首先在tag表中找到tagId，然后到关系表中找到tag下的文章
		BlogTag blogTag = blogTagService.getTagByEn(uid,tagName);
		if(blogTag != null) {
			List<BlogRArticleTag> rs = blogTagService.getArticlesInTag(uid,blogTag.getId());

			List<Long> ids = new ArrayList<Long>();

			if(rs.size() == 0){
				return responseModel;
			}

			for(BlogRArticleTag r : rs){
				ids.add(r.getArticleId());
			}

			List<BlogArticle> l1 = blogArticleService.getArticlesByIds(ids);
			List<BlogArticleDto> l2 = new ArrayList<BlogArticleDto>();

			for(BlogArticle a : l1){
				BlogArticleDto t = new BlogArticleDto(a,null);
				l2.add(t);
			}

			responseModel.setData(l2);
		}else {
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
		}

		return responseModel;
	}

	@RequestMapping(value = "/blog/article/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getArticleById(@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*") ||   !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		BlogArticleDto dto = blogArticleService.getArticleDto(Long.parseLong(id),false);
		if(dto == null){
			responseModel.setResponseCode(ResponseCode.FORBIDDEN.getCode());
			responseModel.setMessage(ResponseCode.FORBIDDEN.getMessage());
			return responseModel;
		}
		responseModel.setData(dto);

		return responseModel;
	}

	@RequestMapping(value = "/blog/article/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getEditArticleById(@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*") ||   !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		BlogArticleDto dto = blogArticleService.getArticleDto(Long.parseLong(id),true);
		if(dto == null){
			responseModel.setResponseCode(ResponseCode.FORBIDDEN.getCode());
			responseModel.setMessage(ResponseCode.FORBIDDEN.getMessage());
			return responseModel;
		}
		responseModel.setData(dto);

		return responseModel;
	}

    /**
     * 需要判断group_id是否在该用户下
     * @param articlePostForm
     * @param bindingResult
     * @return
     */
	@RequestMapping(value = "/blog/article",method = RequestMethod.POST)
	@ResponseBody
	@LoginNeed
	public ResponseModel postNewArticle(@Valid ArticlePostForm articlePostForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogArticle blogArticle = new BlogArticle();
		blogArticle.setName(articlePostForm.getName());
		blogArticle.setView(0);
		Date now = new Date();
		blogArticle.setGmtCreate(now);
		blogArticle.setGmtModified(now);
		blogArticle.setGrammar(Integer.parseInt(articlePostForm.getGrammar()));
		blogArticle.setCreator(JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))));

		BlogArticleContent content = new BlogArticleContent();
		content.setContent(articlePostForm.getContent());
		content.setLength(articlePostForm.getContent().length());

		//增加tags处理部分
        String[] tsS = articlePostForm.getTags() != null ? articlePostForm.getTags().split(",") : new String[]{};
        List<Long> ls = new ArrayList<>(tsS.length);
        for(String ts : tsS){
            ls.add(Long.parseLong(ts));
        }
		BlogArticleDto blogArticleDto = blogArticleService.postArticle(blogArticle,content,Long.parseLong(articlePostForm.getGroup()),ls);

		if(blogArticleDto == null){
			responseModel.setResponseCode(ResponseCode.FORBIDDEN.getCode());
			responseModel.setMessage(ResponseCode.FORBIDDEN.getMessage());
		}else {
			responseModel.setData(blogArticleDto);
		}

		return responseModel;
	}


	/**
	 * 需要判断更新人和文章编写者是否为同一人
	 * @param articleUpdateForm
	 * @return
	 */
	@RequestMapping(value = "/blog/article/update",method = RequestMethod.POST)
	@ResponseBody
	@LoginNeed
	public ResponseModel updateArticle(@Valid ArticleUpdateForm articleUpdateForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogArticle blogArticle = new BlogArticle();
		blogArticle.setId(Long.parseLong(articleUpdateForm.getId()));
		blogArticle.setName(articleUpdateForm.getName());
		Date now = new Date();
		blogArticle.setGmtModified(now);
		blogArticle.setGrammar(Integer.parseInt(articleUpdateForm.getGrammar()));

		BlogArticleContent content = new BlogArticleContent();
		content.setId(blogArticle.getId());
		content.setContent(articleUpdateForm.getContent());
		content.setLength(articleUpdateForm.getContent().length());
		content.setGmtModified(now);

        //增加tags处理部分
        String[] tsS = articleUpdateForm.getTags() != null ? articleUpdateForm.getTags().split(",") : new String[]{};
        List<Long> ls = new ArrayList<>(tsS.length);
        for(String ts : tsS){
            ls.add(Long.parseLong(ts));
        }
		Integer result = blogArticleService.updateArticle(blogArticle,content, JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))),Long.parseLong(articleUpdateForm.getGroup()),ls);

		if(result.equals(-1)){
			responseModel.setResponseCode(ResponseCode.FORBIDDEN.getCode());
			responseModel.setMessage(ResponseCode.FORBIDDEN.getMessage());
		}

		return responseModel;
	}

	/**
	 * 需要给service传入accountId，
	 * 暂时不开放
	 * @param id
	 * @return
	 */
	public ResponseModel deleteArticle(Long id){
		ResponseModel responseModel = new ResponseModel();
		Long accountId = (Long)httpServletRequest.getSession().getAttribute("user_id");

		blogArticleService.deleteArticle(id,accountId);

		return responseModel;
	}
}
