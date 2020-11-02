package com.myblog.yu.service;

import com.myblog.yu.bean.ArticleInfo;
import com.myblog.yu.utils.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章管理业务逻辑的接口
 * @author 容
 * @version 1.0
 * @date 2020/7/19 0:38
 */
public interface ArticleService {

    /**
     * 添加文章
     * @param articleInfo
     * @return
     */
    public boolean add(ArticleInfo articleInfo);

    /**
     * 文件上传
     * @param file
     * @return
     */
    public String doPutFile(MultipartFile file);

    /**
     * 根据条件查询文章信息
     * @param info
     * @param page
     * @return
     */
    public PageBean<ArticleInfo> getList(ArticleInfo info, Integer page);

    /**
     * 根据编号查询文章信息
     * @param info
     * @return
     */
    public ArticleInfo getArticleInfo(ArticleInfo info);

    /**
     * 修改文章
     * @param articleInfo
     * @return
     */
    public boolean update(ArticleInfo articleInfo);

    /**
     * 根据文章编号删除文章信息（真删除）
     * @param articleId
     * @return
     */
    public boolean delete(Integer articleId);

    /**
     * 前台，查询最新的15条文章信息
     * @return
     */
    public List<ArticleInfo> getNewList();

    /**
     * 前台，查询站长推荐的10条记录信息
     * @return
     */
    public List<ArticleInfo> getArtList();
}
