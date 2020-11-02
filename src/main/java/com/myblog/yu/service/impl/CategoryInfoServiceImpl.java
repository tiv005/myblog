package com.myblog.yu.service.impl;

import com.myblog.yu.bean.CategoryInfo;
import com.myblog.yu.dao.ArticleInfoMapper;
import com.myblog.yu.dao.CategoryInfoMapper;
import com.myblog.yu.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/17 23:22
 */
@Service
public class CategoryInfoServiceImpl implements CategoryInfoService {

    /**
     * 栏目管理的mapper
     */
    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    /**
     * 文章管理的mapper
     */
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    /**
     * 显示所有栏目信息
     * @return
     */
    @Cacheable(cacheNames = "yu",key = "#p0")
    public List<CategoryInfo> getCategoryList(CategoryInfo info) {
        return categoryInfoMapper.getCategoryList();
    }

    /**
     * 添加栏目信息
     * @param categoryInfo
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "yu",allEntries = true)
    public boolean add(CategoryInfo categoryInfo) {
        try {
            int count = categoryInfoMapper.insertSelective(categoryInfo);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据栏目编号查询栏目信息
     * @param categoryId
     * @return
     */
    @Override
    public CategoryInfo getCategoryInfo(Integer categoryId) {
        try{
            return categoryInfoMapper.selectByPrimaryKey(categoryId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改栏目信息
     * @param categoryInfo
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "yu",allEntries = true)
    public boolean update(CategoryInfo categoryInfo) {
        try {
            int count = categoryInfoMapper.updateByPrimaryKeySelective(categoryInfo);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Transactional  //事务处理
    @CacheEvict(cacheNames = "yu",allEntries = true)
    public void delete(Integer categoryId)throws Exception {
        //先删除文章信息
        articleInfoMapper.deleteCategoryId(categoryId);
        //再删除栏目信息
        categoryInfoMapper.deleteByPrimaryKey(categoryId);
    }
}
