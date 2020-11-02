package com.myblog.yu.controller.front;

import com.myblog.yu.bean.ArticleInfo;
import com.myblog.yu.bean.ArticleInfoBean;
import com.myblog.yu.bean.CategoryInfo;
import com.myblog.yu.bean.MessageInfo;
import com.myblog.yu.dao.ArticleInfoBeanRepository;
import com.myblog.yu.service.ArticleService;
import com.myblog.yu.service.CategoryInfoService;
import com.myblog.yu.service.MessageService;
import com.myblog.yu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


;
import java.util.List;

/**
 * 前台展示控制器
 * @author 容
 * @version 1.0
 * @date 2020/7/24 10:13
 */
@Controller
@RequestMapping("/")
public class FrontIndexController {

    /**
     * 栏目管理的业务逻辑接口
     */
    @Autowired
    private CategoryInfoService categoryInfoService;

    /**
     * 文章管理的业务逻辑接口
     */
    @Autowired
    private ArticleService articleService;

    /**
     * 留言管理的业务逻辑接口
     */
    @Autowired
    private MessageService messageService;

    @Autowired
    private ArticleInfoBeanRepository er;


    /**
     * 提取出共同部分
     * @param model
     */
    public void init(Model model){
        //查询栏目
        CategoryInfo info = new CategoryInfo();
        List<CategoryInfo> categoryInfoList = categoryInfoService.getCategoryList(info);
        model.addAttribute("categoryInfoList",categoryInfoList);

        //查询站长推荐的10条文章信息
        List<ArticleInfo> rlist = articleService.getArtList();
        model.addAttribute("rlist",rlist);
    }
    /**
     * 首页展示信息
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){

        init(model);
        //查询最新的15条文章信息
        List<ArticleInfo> artList = articleService.getNewList();
        model.addAttribute("artList",artList);



        return "index";
    }

    /**
     * 查看点击对应详情文章
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("listView/{id}")
    public String listView(@PathVariable("id") Integer id,Model model){
        init(model);
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setArticleId(id);
        articleInfo = articleService.getArticleInfo(articleInfo);
        model.addAttribute("articleInfo",articleInfo);
        return "listview";
    }

    /**
     * 根据栏目的类别查询文章信息
     * @param info
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("list")
    public String list(ArticleInfo info,Model model,Integer page){
        init(model);
        PageBean<ArticleInfo> pageBean = articleService.getList(info,page);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("artInfo",info);
        return "list";
    }

    /**
     * 显示需要的留言信息
     * @param model
     * @return
     */
    @RequestMapping("message")
    public String message(Model model){
        init(model);
        List<MessageInfo> melist = messageService.getMessagesList();
        model.addAttribute("melist",melist);
        return "message";
    }

    /**
     * 前台，在线添加留言信息,不进行重定向就就需要中转页面，防止重新提交
     * @param info
     * @param model
     * @return
     */
    @RequestMapping("addMessage")
    public String addMessage(MessageInfo info, Model model){
        init(model);
        boolean mark = messageService.add(info);
        if (mark){
            model.addAttribute("meinfo","留言成功");
        }else {
            model.addAttribute("meinfo","留言成功");
        }
        //显示添加后的留言信息
        List<MessageInfo> melist = messageService.getMessagesList();
        model.addAttribute("melist",melist);
        return "/message_info";
    }

    /**
     * es全文检索
     * @param name
     * @return
     */
    @RequestMapping("es")
    public String getlist(String name,Model model){
        init(model);
        Pageable page = new PageRequest(0,50);
        Page<ArticleInfoBean> pages =
                er.findDistinctArticleInfoBeanByArticleTitleContainingOrArticleContentContaining(name,name,page);
        List<ArticleInfoBean> list = pages.getContent();
        model.addAttribute("eslist",list);
        return "es";
    }
}
