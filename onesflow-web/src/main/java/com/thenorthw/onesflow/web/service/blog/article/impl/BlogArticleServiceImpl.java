package com.thenorthw.onesflow.web.service.blog.article.impl;

import com.thenorthw.onesflow.common.dao.blog.article.BlogArticleContentDao;
import com.thenorthw.onesflow.common.dao.blog.article.BlogArticleDao;
import com.thenorthw.onesflow.common.dao.blog.group.BlogRArticleGroupDao;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticle;
import com.thenorthw.onesflow.common.model.blog.article.BlogArticleContent;
import com.thenorthw.onesflow.common.model.blog.tag.BlogRArticleTag;
import com.thenorthw.onesflow.common.model.blog.tag.BlogTag;
import com.thenorthw.onesflow.face.dto.blog.article.BlogArticleDto;
import com.thenorthw.onesflow.web.service.blog.article.BlogArticleService;
import com.thenorthw.onesflow.web.service.blog.group.BlogGroupService;
import com.thenorthw.onesflow.web.service.blog.tag.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @autuor theNorthW
 * @date 23/09/2017.
 * blog: thenorthw.com
 */
@Service
@Transactional
public class BlogArticleServiceImpl implements BlogArticleService {
	@Autowired
	BlogArticleDao blogArticleDao;
	@Autowired
	BlogArticleContentDao blogArticleContentDao;

	@Autowired
	BlogGroupService blogGroupService;
	@Autowired
	BlogRArticleGroupDao blogRArticleGroupDao;

	@Autowired
	BlogTagService blogTagService;

	/**
	 * 单纯的获取article的summary信息
	 * 浏览量+1
	 * @param
	 * @return
	 */
	public BlogArticle getArticleInfo(Long aid) {
		blogArticleDao.updateArticleView(aid);
		return blogArticleDao.getArticleById(aid);
	}

	/**
	 * 获取单个article的内容信息
	 * @param
	 * @return
	 */
	public BlogArticleContent getArticleContent(Long aid) {
		return blogArticleContentDao.getArticleContent(aid);
	}

	public List<BlogArticleDto> getRecentArticles(Long id){
		//默认获取最近10篇
		//获取最近的article信息和article content
        List<BlogArticle> bas = blogArticleDao.getRecentArticles(id,10);

        List<BlogArticleDto> dtos = new ArrayList<>(10);

        for(BlogArticle b : bas){
        	BlogArticleContent blogArticleContent = getArticleContent(b.getId());
        	blogArticleContent.setContent(blogArticleContent.getContent().substring(0,blogArticleContent.getLength() > 300?300:blogArticleContent.getLength()));
        	BlogArticleDto blogArticleDto = new BlogArticleDto(b,blogArticleContent);
        	blogArticleDto.setGroup(blogGroupService.getGroupIdByArticleId(b.getId()).getGroupId());
            List<BlogRArticleTag> brtl = blogTagService.getTagIdByArticleId(id);
            List<Long> ls = new ArrayList<>(brtl.size());
            for(BlogRArticleTag b1 : brtl){
                ls.add(b1.getTagId());
            }
            blogArticleDto.setTags(ls);
        	dtos.add(blogArticleDto);
		}

		return dtos;
	}

	public List<BlogArticle> getArticlesByIds(List<Long> ids){
		return blogArticleDao.getArticleByIds(ids);
	}

	/**
	 * 获取所有文章的元信息，根据page来进行返回
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<BlogArticleDto> getAllArticles(Long uid, int pageNumber, int pageSize) {
		List<BlogArticle>  list = blogArticleDao.getAllArticles(uid,pageNumber*pageSize, pageSize);
		List<BlogArticleDto> result = new ArrayList<BlogArticleDto>();
		for(BlogArticle a : list){
			BlogArticleDto blogArticleDto = new BlogArticleDto(a,null);
			blogArticleDto.setGroup(blogGroupService.getGroupIdByArticleId(a.getId()).getGroupId());
			result.add(blogArticleDto);
		}
		return result;
	}

	public List<BlogArticleContent> getArticleContents(List<Long> ids){
		//最多获取10篇
		if(ids.size() > 10){
			return null;
		}
		return blogArticleContentDao.getArticleContentsByIds(ids);
	}


	/**
	 * ！注意需要将view次数+1
	 * @param id
	 * @return
	 */
	public BlogArticleDto getArticleDto(Long id, boolean edit) {
		BlogArticle blogArticle = blogArticleDao.getArticleById(id);
		if(blogArticle == null){
			return null;
		}
		if(!edit) {
			blogArticleDao.updateViewTimes(id);
		}
		BlogArticleContent blogArticleContent = blogArticleContentDao.getArticleContent(id);
		BlogArticleDto blogArticleDto = new BlogArticleDto(blogArticle, blogArticleContent);
		blogArticleDto.setGroup(blogGroupService.getGroupIdByArticleId(id).getGroupId());
		List<BlogRArticleTag> brtl = blogTagService.getTagIdByArticleId(id);
		List<Long> ls = new ArrayList<>(brtl.size());
		for(BlogRArticleTag b : brtl){
		    ls.add(b.getTagId());
        }
		blogArticleDto.setTags(ls);
		return blogArticleDto;
	}

    public int getTotalArticleNumber(Long uid) {
        return blogArticleDao.getTotalNumber(uid);
    }



    /**
	 * 将article基本信息和content一起上传，group稍后上传，tag也是稍后通过调用其他api进行上传
	 * @param blogArticle
	 * @param blogArticleContent
	 * @return 返回article id
	 */
	public BlogArticleDto postArticle(BlogArticle blogArticle, BlogArticleContent blogArticleContent, Long groupId,List<Long> tags) {
		//首先需要判断这个groupId是否在这个用户下面,Tag也要判定
		if(!checkAGT(null,blogArticle.getCreator(),groupId,tags,false)){
		    return null;
        }

		blogArticleDao.postArticle(blogArticle);
		blogArticleContentDao.postArticleContent(blogArticleContent);

		//添加article和group的关系
		//判断group是否存在
		//通过RArticleGroupService来进行插入
		blogGroupService.linkArticleAndGroup(blogArticle.getId(),groupId);
		blogTagService.linkArticleAndTag(blogArticle.getId(),tags);

		BlogArticleDto dto = new BlogArticleDto(blogArticle, blogArticleContent);
		dto.setGroup(groupId);
		return dto;
	}

	/**
	 * 需要删除info和content，还要删除该article和group和tag的map
	 * 那么问题来了，中途删除失败怎么办，那就不知道让她随风吧
	 * @param id
	 * @return
	 */
	public int deleteArticle(Long id, Long accountId) {
		blogArticleDao.deleteArticle(id);
		blogArticleContentDao.deleteArticleContent(id);
		blogRArticleGroupDao.deleteRByArticleId(id);
//		rArticleTagDao.deleteRByArticleId(id);
		return 0;
	}

	/**
	 * 该方法只用来更新文章内容
	 * 需要判断是否是文章创建者进行的修改
	 * @return
	 */
	public int updateArticle(BlogArticle blogArticle, BlogArticleContent blogArticleContent, Long postId,Long groupId,List<Long> tags) {

		//只有发表文章的人才能修改文章
		if(!checkAGT(blogArticle.getId(),postId,groupId,tags,true)){
            return -1;
        }

        blogArticleDao.updateArticle(blogArticle);
		blogArticleContentDao.updateArticleContent(blogArticleContent);
        blogGroupService.linkArticleAndGroup(blogArticle.getId(),groupId);
        blogTagService.linkArticleAndTag(blogArticle.getId(),tags);

        return 1;

    }


	//确认要更新或者要发布的文章 是否满足各个部分之间的关联关系

    /**
     *
     * @param aid
     * @param uid 发起这个操作的人，也就是x-token中的id
     * @param gid
     * @param update
     * @return
     */
	private boolean checkAGT(Long aid,Long uid,Long gid,List<Long> tags,boolean update){
	    if(update){
            BlogArticle inDb = blogArticleDao.getArticleById(aid);
            //表示并不存在这篇文章
            if(inDb == null){
                return false;
            }
            if(!uid.equals(inDb.getCreator())){
                return false;
            }
        }

        //判断是否groupId是否在这个用户下
        boolean hasGroup = blogGroupService.getGroupById(uid,gid) != null;

        if(!hasGroup){
            return false;
        }

        //判断tags是否在用户下
		List<BlogTag> blogTags = blogTagService.getAllTags(uid);
        List<Long> ls = new ArrayList<>(blogTags.size());

        for(BlogTag bt : blogTags){
        	ls.add(bt.getId());
        }
        if(!ls.containsAll(tags)){
            return false;
        }

        return true;
    }


}
