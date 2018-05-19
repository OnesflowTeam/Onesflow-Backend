package com.thenorthw.onesflow.web.controller.blog.tag;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.NumberAssert;
import com.thenorthw.onesflow.face.form.blog.tag.TagPostForm;
import com.thenorthw.onesflow.face.form.blog.tag.TagUpdateForm;
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
import java.util.List;

/**
 * @autuor theNorthW
 * @date 19/09/2017.
 * blog: thenorthw.com
 */
@Controller
@RequestMapping(value = "/web/v1")
public class TagController {
	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	BlogTagService blogTagService;

	/**
	 * 这个方法是直接获取所有的tag（二维数组形式返回给前端），所以不需要参数
	 * service此处还会做一个缓存操作，因为article tag不会有太大变动，这样就可以减轻数据库压力
	 * @return
	 */
	@RequestMapping(value = "/blog/u/{id}/tags", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getAllArticleTags(@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*") || !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		List<BlogTag> articleBlogTagList = blogTagService.getAllTags(Long.parseLong(id));

		//是否以树形结构输出，感觉不需要呀 - -

		if(articleBlogTagList == null){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage("Get failed, unknown error.");
		}else{
			responseModel.setData(articleBlogTagList);
		}

		return responseModel;
	}

	/**
	 * 不允许重名的tag出现
	 * @param tagPostForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/blog/tag", method = RequestMethod.POST)
	@ResponseBody
	@LoginNeed
	public ResponseModel addArticleTags(@Valid TagPostForm tagPostForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogTag articleBlogTag = new BlogTag();
		articleBlogTag.setName(tagPostForm.getName());
		articleBlogTag.setEn(tagPostForm.getEn());
		articleBlogTag.setUid(JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))));
		int res = blogTagService.addTag(articleBlogTag);

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}else if(res == -4){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_TAG.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_TAG.getMessage());
		}else if(res == -5){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_TAG_EN.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_TAG_EN.getMessage());
		}

		return responseModel;
	}


	/**
	 * 更新时候也不允许重名
	 * @param tagUpdateForm
	 * @param bindingResult
	 * @return
	 */
//	@RequestMapping(value = "/blog/tag/info", method = RequestMethod.POST)
//	@ResponseBody
//	@LoginNeed
	public ResponseModel updateArticleTag(TagUpdateForm tagUpdateForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogTag articleBlogTag = new BlogTag();
		articleBlogTag.setId(tagUpdateForm.getId());
		articleBlogTag.setName(tagUpdateForm.getName());
		articleBlogTag.setEn(tagUpdateForm.getEn());

		int res = blogTagService.updateTag(JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))),articleBlogTag);

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}else if(res == -4){
			responseModel.setResponseCode(ResponseCode.NO_SUCH_TAG.getCode());
			responseModel.setMessage(ResponseCode.NO_SUCH_TAG.getMessage());
		}else if(res == -6){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_TAG.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_TAG.getMessage());
		}else if(res == -5){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_TAG_EN.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_TAG_EN.getMessage());
		}
		return responseModel;
	}


	/**
	 * 暂时不允许tag的删除，因为删除后对文章影响较大
	 * 作为个人博客使用时候，会实现有一个默认分类，默认分类不允许被删除，不然的话，删除后就会有文章不属于分类里
	 * 作为中间件网站时，会事先有分类，用户post文章时，只需要将文章正确分类即可
	 * 用户想增加分类，则可以申请表单有管理员进行操作
	 */
	public ResponseModel deleteArticleTags(String id){
		ResponseModel responseModel = new ResponseModel();

		Long idL = Long.parseLong(id);
//		int res = blogTagService.deleteArticleTag(idL);
		int res = -1;

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.TAG_DELETE_ERROR.getCode());
			responseModel.setMessage(ResponseCode.TAG_DELETE_ERROR.getMessage());
			return responseModel;
		}

		//需要删除和这个分类相关的文章


		return responseModel;
	}
}