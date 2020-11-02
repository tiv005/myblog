package com.myblog.yu.controller.back;

import com.myblog.yu.bean.CategoryInfo;
import com.myblog.yu.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 栏目管理
 * @author 容
 * @version 1.0
 * @date 2020/7/15 21:45
 */
@Controller
@RequestMapping("/back/category/")
public class CategoryController {

    @Autowired
    private CategoryInfoService categoryInfoService;

    /**
     * 查询所有栏目的信息
     * @param model
     * @return
     */
    @RequestMapping("category")
    public String category(Model model){
        CategoryInfo info = new CategoryInfo();
        List<CategoryInfo> categoryList = categoryInfoService.getCategoryList(info);
        model.addAttribute("categoryList",categoryList);
        return "/back/category/category";
    }

    /**
     * 修改栏目信息
     * @param categoryInfo
     * @param model
     * @return
     */
    @RequestMapping("update")
    public String update(CategoryInfo categoryInfo ,Model model){
        boolean update = categoryInfoService.update(categoryInfo);
        if (update){
            model.addAttribute("info","修改栏目信息成功");
        }else {
            model.addAttribute("info","修改栏目信息失败");
        }
        //将更新的数据显示在页面上
        model.addAttribute("cateInfo",categoryInfo);
        return "/back/category/category_update";
    }

    /**
     * 添加栏目信息
     * @param categoryInfo
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String add(CategoryInfo categoryInfo,Model model){
        boolean mark = categoryInfoService.add(categoryInfo);
        if (mark){
            model.addAttribute("info","添加栏目信息成功");
        }else {
            model.addAttribute("info","添加栏目信息失败");
        }

        return "/back/category/category_info";
    }

    /**
     * 根据编号查询栏目信息
     * @param categoryId
     * @param model
     * @return
     */
    @RequestMapping("get/{id}")
    public String loadUpdate(@PathVariable("id") Integer categoryId,Model model){
        CategoryInfo categoryInfo = categoryInfoService.getCategoryInfo(categoryId);
        model.addAttribute("cateInfo",categoryInfo);
        return "/back/category/category_update";
    }

    @RequestMapping("del/{id}")
    public String delete(@PathVariable("id") Integer categoryId,Model model){
        try{
            categoryInfoService.delete(categoryId);
            model.addAttribute("info","删除栏目信息成功");
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("info","删除栏目信息失败");
        }
        return "/back/category/category_info";
    }
}
