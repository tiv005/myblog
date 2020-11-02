package com.myblog.yu.controller.back;

import com.myblog.yu.bean.ArticleInfo;
import com.myblog.yu.bean.CategoryInfo;
import com.myblog.yu.bean.UserInfo;
import com.myblog.yu.service.ArticleService;
import com.myblog.yu.service.CategoryInfoService;
import com.myblog.yu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * 文章管理
 * @author 容
 * @version 1.0
 * @date 2020/7/15 21:39
 */
@Controller
@RequestMapping("/back/article/")
public class ArticleController {

    /**
     * 栏目管理业务逻辑接口
     */
    @Autowired
    CategoryInfoService categoryInfoService;

    /**
     * 文章管理业务逻辑接口
     */
    @Autowired
    ArticleService articleService;

    /**
     * 加载文章的添加页面
     * @return
     */
    @RequestMapping("loadAdd")
    public String loadAdd(Model model){
        CategoryInfo categoryInfo = new CategoryInfo();
        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(categoryInfo);
        model.addAttribute("categoryList",categoryList);
        return "/back/article/article_add";
    }

    /**
     * 添加文章信息
     * @param articleInfo
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String add(ArticleInfo articleInfo, Model model, HttpSession session){

        //获取session中用户信息
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        System.out.println(userInfo);
        if (userInfo!=null){
            //获取用户的id
            articleInfo.setUserId(userInfo.getUserId());
            //获取最新时间
            articleInfo.setArticleTime(new Date());
        }
        boolean mark = articleService.add(articleInfo);
        if(mark){
            model.addAttribute("info","添加文章成功");
        }else {
            model.addAttribute("info","添加文章失败");
        }

        //回调添加后的信息
        CategoryInfo categoryInfo = new CategoryInfo();
        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(categoryInfo);
        model.addAttribute("categoryList",categoryList);
        return "/back/article/article_add";
    }

    /**
     * 根据条件查询文章信息
     * @return
     */
    @RequestMapping("list")
    public String list(ArticleInfo info,Model model,Integer page){
        CategoryInfo categoryInfo = new CategoryInfo();
        PageBean<ArticleInfo> pageBean = articleService.getList(info,page);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("artInfo",info);

        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(categoryInfo);
        model.addAttribute("categoryList",categoryList);
        return "/back/article/article_list";
    }

    @RequestMapping("update")
    public String update(ArticleInfo articleInfo, Model model, HttpSession session){
        //获取session中用户信息
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        System.out.println(userInfo);
        if (userInfo!=null){
            //获取用户的id
            articleInfo.setUserId(userInfo.getUserId());
            //获取最新时间
            articleInfo.setArticleTime(new Date());
        }
        boolean mark = articleService.update(articleInfo);
        if(mark){
            model.addAttribute("info","修改文章成功");
        }else {
            model.addAttribute("info","修改文章失败");
        }

        //回调添加后的信息
        CategoryInfo categoryInfo = new CategoryInfo();
        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(categoryInfo);
        model.addAttribute("categoryList",categoryList);
        //修改后的值显示在修改页面上
        model.addAttribute("articleInfo",articleInfo);
        return "/back/article/article_update";
    }


    /**
     * 上传图片
     * @param upload
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile upload){
        String url = articleService.doPutFile(upload);
        return url;
    }

    /**
     * 在线文本编辑器上传图片（富文本编辑器）
     * @param upload
     * @param request
     * @param response
     */
    @RequestMapping("uploadfile")
    public void uploadfile(@RequestParam MultipartFile upload,
                           HttpServletRequest request, HttpServletResponse response){
        try{
            String url = articleService.doPutFile(upload);
            PrintWriter out = response.getWriter();
            String callBack = request.getParameter("CKEditorFuncNum");
            //回调
            out.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callBack + ",'" + url + "')</script>");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据编号查询文章信息，并将信息显示在修改页面
     * @param artInfo
     * @return
     */
    @RequestMapping("loadUpdate")
    public String loadUpdate(ArticleInfo artInfo,Model model){

        ArticleInfo articleInfo = articleService.getArticleInfo(artInfo);
        model.addAttribute("articleInfo",articleInfo);
        CategoryInfo categoryInfo = new CategoryInfo();
        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(categoryInfo);
        model.addAttribute("categoryList",categoryList);
        return "/back/article/article_update";
    }

    /**
     * 根据编号删除文章信息
     * @param
     * @param model
     * @return
     */
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id,Model model){
        boolean mark = articleService.delete(id);
        if(mark){
            model.addAttribute("info","删除文章成功");
        }else {
            model.addAttribute("info","删除文章失败");
        }
        return "/back/article/article_info";
    }
}
