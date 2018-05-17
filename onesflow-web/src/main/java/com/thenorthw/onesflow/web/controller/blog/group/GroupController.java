package com.thenorthw.onesflow.web.controller.blog.group;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.annotation.LoginNeed;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import com.thenorthw.onesflow.common.model.blog.group.BlogGroup;
import com.thenorthw.onesflow.common.utils.JwtUtil;
import com.thenorthw.onesflow.common.utils.NumberAssert;
import com.thenorthw.onesflow.face.form.blog.group.GroupPostForm;
import com.thenorthw.onesflow.face.form.blog.group.GroupUpdateForm;
import com.thenorthw.onesflow.web.service.blog.group.BlogGroupService;
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
public class GroupController {
	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	BlogGroupService blogGroupService;

	/**
	 * 这个方法是直接获取所有的group（二维数组形式返回给前端），所以不需要参数
	 * service此处还会做一个缓存操作，因为article group不会有太大变动，这样就可以减轻数据库压力
	 * @return
	 */
	@RequestMapping(value = "/blog/u/{id}/group", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel getAllArticleGroups(@PathVariable String id){
		ResponseModel responseModel = new ResponseModel();

		if(!id.matches("^[1-9]\\d*") || !NumberAssert.isLong(id)){
			responseModel.setResponseCode(ResponseCode.PARAMETER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.PARAMETER_ERROR.getMessage());
			return responseModel;
		}

		List<BlogGroup> articleBlogGroupList = blogGroupService.getAllGroups(Long.parseLong(id));

		//是否以树形结构输出，感觉不需要呀 - -

		if(articleBlogGroupList == null){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage("Get failed, unknown error.");
		}else{
			responseModel.setData(articleBlogGroupList);
		}

		return responseModel;
	}

	/**
	 * 不允许重名的group出现
	 * @param groupPostForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/blog/group", method = RequestMethod.POST)
	@ResponseBody
	@LoginNeed
	public ResponseModel addArticleGroups(@Valid GroupPostForm groupPostForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogGroup articleBlogGroup = new BlogGroup();
		articleBlogGroup.setName(groupPostForm.getName());
		articleBlogGroup.setEn(groupPostForm.getEn());
		articleBlogGroup.setUid(JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))));
		int res = blogGroupService.addGroup(articleBlogGroup);

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}else if(res == -4){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_GROUP.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_GROUP.getMessage());
		}else if(res == -5){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_GROUP_EN.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_GROUP_EN.getMessage());
		}

		return responseModel;
	}


	/**
	 * 更新时候也不允许重名
	 * @param groupUpdateForm
	 * @param bindingResult
	 * @return
	 */
//	@RequestMapping(value = "/blog/group/info", method = RequestMethod.POST)
//	@ResponseBody
//	@LoginNeed
	public ResponseModel updateArticleGroup(GroupUpdateForm groupUpdateForm, BindingResult bindingResult){
		ResponseModel responseModel = new ResponseModel();

		BlogGroup articleBlogGroup = new BlogGroup();
		articleBlogGroup.setId(groupUpdateForm.getId());
		articleBlogGroup.setName(groupUpdateForm.getName());
		articleBlogGroup.setEn(groupUpdateForm.getEn());

		int res = blogGroupService.updateGroup(JwtUtil.getUidFromClaims(JwtUtil.verify(httpServletRequest.getHeader(OnesflowConstant.TOKEN_HEADER))),articleBlogGroup);

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
			responseModel.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}else if(res == -4){
			responseModel.setResponseCode(ResponseCode.NO_SUCH_GROUP.getCode());
			responseModel.setMessage(ResponseCode.NO_SUCH_GROUP.getMessage());
		}else if(res == -2){
			responseModel.setResponseCode(ResponseCode.NO_SUCH_PARENT_GROUP.getCode());
			responseModel.setMessage(ResponseCode.NO_SUCH_PARENT_GROUP.getMessage());
		}else if(res == -3){
			responseModel.setResponseCode(ResponseCode.PARENT_LEVEL_ILLEGAL.getCode());
			responseModel.setMessage(ResponseCode.PARENT_LEVEL_ILLEGAL.getMessage());
		}else if(res == -6){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_GROUP.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_GROUP.getMessage());
		}else if(res == -5){
			responseModel.setResponseCode(ResponseCode.DUPLICATED_GROUP_EN.getCode());
			responseModel.setMessage(ResponseCode.DUPLICATED_GROUP_EN.getMessage());
		}
		return responseModel;
	}


	/**
	 * 暂时不允许group的删除，因为删除后对文章影响较大
	 * 作为个人博客使用时候，会实现有一个默认分类，默认分类不允许被删除，不然的话，删除后就会有文章不属于分类里
	 * 作为中间件网站时，会事先有分类，用户post文章时，只需要将文章正确分类即可
	 * 用户想增加分类，则可以申请表单有管理员进行操作
	 */
	public ResponseModel deleteArticleGroups(String id){
		ResponseModel responseModel = new ResponseModel();

		Long idL = Long.parseLong(id);
//		int res = blogGroupService.deleteArticleGroup(idL);
		int res = -1;

		if(res == -1){
			responseModel.setResponseCode(ResponseCode.GROUP_DELETE_ERROR.getCode());
			responseModel.setMessage(ResponseCode.GROUP_DELETE_ERROR.getMessage());
			return responseModel;
		}

		//需要删除和这个分类相关的文章


		return responseModel;
	}
}