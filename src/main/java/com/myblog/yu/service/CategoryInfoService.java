package com.myblog.yu.service;

import com.myblog.yu.bean.CategoryInfo;

import java.util.List;

/**
 * 栏目管理的业务逻辑接口
 * @author 容
 * @version 1.0
 * @date 2020/7/17 23:20
 */
public interface CategoryInfoService {

    /**
     * 查询所有栏目的信息
     * @return
     */
    public List<CategoryInfo> getCategoryList(CategoryInfo info);

    /**
     * 添加栏目信息
     * @param categoryInfo
     * @return
     */
    public boolean add(CategoryInfo categoryInfo);

    /**
     * 根据栏目编号查询栏目信息
     * @param categoryId
     * @return
     */
    public CategoryInfo getCategoryInfo(Integer categoryId);

    /**
     * 修改栏目信息
     * @param categoryInfo
     * @return
     */
    public boolean update(CategoryInfo categoryInfo);

    /**
     * 根据栏目的编号删除栏目，以及栏目下的所有的文章
     * @param categoryId
     */
    public void delete(Integer categoryId) throws Exception;

}
