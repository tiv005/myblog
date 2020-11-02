package com.myblog.yu.dao;

import com.myblog.yu.bean.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryInfoMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(CategoryInfo record);

    int insertSelective(CategoryInfo record);

    CategoryInfo selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(CategoryInfo record);

    int updateByPrimaryKey(CategoryInfo record);

    /**
     * 查询所有栏目信息
     * @return
     */
    public List<CategoryInfo> getCategoryList();
}