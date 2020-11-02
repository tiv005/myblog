package com.myblog.yu.service.impl;

import com.myblog.yu.bean.ArticleInfo;
import com.myblog.yu.bean.ArticleInfoBean;
import com.myblog.yu.dao.ArticleInfoBeanRepository;
import com.myblog.yu.dao.ArticleInfoMapper;
import com.myblog.yu.service.ArticleService;
import com.myblog.yu.utils.Const;
import com.myblog.yu.utils.PageBean;
import com.myblog.yu.utils.PageUtils;
import com.sun.deploy.net.URLEncoder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/19 0:39
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private ArticleInfoBeanRepository er;

    /**
     * 添加文章
     * @param articleInfo
     * @return
     */
    @Override
    public boolean add(ArticleInfo articleInfo) {
        try{
            int count = articleInfoMapper.insertSelective(articleInfo);


            //保存到es中
            ArticleInfoBean aib = new ArticleInfoBean();
            aib.setArticleId(articleInfo.getArticleId());
            aib.setArticleTitle(articleInfo.getArticleTitle());
            aib.setArticleContent(articleInfo.getArticleContent());
            aib.setArticleImg(articleInfo.getArticleImg());
            aib.setArticleTime(articleInfo.getArticleTime());
            er.save(aib);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 上传图片到Tomcat服务器上，wepApps文件下
     * @param file
     * @return
     */
    @Override
    public String doPutFile(MultipartFile file) {
        try{
            //图片名称
            String fileName = file.getOriginalFilename();
            String encodeFileName = URLEncoder.encode(fileName, "utf-8");

            //当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(new Date());

            //名称的命名方式：地址+名称+当前时间
            String url = Const.FILE_URL+format+encodeFileName;
            System.out.println(url);

            //Jersey 客户端
            Client client = new Client();
            WebResource resource = client.resource(url);

            //将文件转为byte
            byte[] buf = file.getBytes();
            resource.put(String.class,buf);

            return url;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据条件查询文章
     * @param artInfo
     * @param page
     * @return
     */
    @Override
    public PageBean<ArticleInfo> getList(ArticleInfo artInfo, Integer page) {
        //总记录数
        int allRow = articleInfoMapper.getArticleCount(artInfo).intValue();
        //总页数
        int totalPage = PageUtils.countTotalPage(allRow, Const.PAGE_SIZE);
        //当前第几页
        int currentPage = PageUtils.countCurrentPage(page);
        //起始记录数
        int start = PageUtils.countStart(Const.PAGE_SIZE,currentPage);

        if(page>=0){
            artInfo.setStart(start);
            artInfo.setLength(Const.PAGE_SIZE);
        }else {
            artInfo.setStart(-1);
            artInfo.setStart(-1);
        }

        List<ArticleInfo> arts = articleInfoMapper.getArticleList(artInfo);

        PageBean<ArticleInfo> pageBean = new PageBean<>();
        pageBean.setAllRow(allRow);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        pageBean.setList(arts);

        return pageBean;
    }

    /**
     * 根据编号查询文章信息
     * @param info
     * @return
     */
    @Override
    public ArticleInfo getArticleInfo(ArticleInfo info) {
        try{
            return articleInfoMapper.selectByPrimaryKey(info.getArticleId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改文章
     * @param articleInfo
     * @return
     */
    @Override
    public boolean update(ArticleInfo articleInfo) {
        try{
            int count = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);

            //修改
            ArticleInfoBean aib = new ArticleInfoBean();
            aib.setArticleId(articleInfo.getArticleId());
            aib.setArticleTitle(articleInfo.getArticleTitle());
            aib.setArticleContent(articleInfo.getArticleContent());
            aib.setArticleImg(articleInfo.getArticleImg());
            aib.setArticleTime(articleInfo.getArticleTime());
            er.save(aib);

            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 根据文章编号删除文章信息
     * @param articleId
     * @return
     */
    @Override
    public boolean delete(Integer articleId) {
        try{
            int count = articleInfoMapper.deleteByPrimaryKey(articleId);

            //删除
            ArticleInfoBean aib = new ArticleInfoBean();
            aib.setArticleId(articleId);
            er.delete(aib);

            if(count>0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 前台，查询最新的15条文章信息
     * @return
     */
    @Override
    public List<ArticleInfo> getNewList() {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setArticleMark(Const.MARK_YES);
        articleInfo.setStart(0);
        articleInfo.setLength(15);
        List<ArticleInfo> articleList = articleInfoMapper.getArticleList(articleInfo);
        return articleList;
    }

    /**
     * 前台，查询站长推荐的10条记录信息
     * @return
     */
    @Override
    public List<ArticleInfo> getArtList() {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setArticleRecom(Const.MARK_YES);
        articleInfo.setArticleMark(Const.MARK_YES);
        articleInfo.setStart(0);
        articleInfo.setLength(10);
        List<ArticleInfo> articleList = articleInfoMapper.getArticleList(articleInfo);
        return articleList;
    }
}
