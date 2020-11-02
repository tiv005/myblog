package com.myblog.yu.dao;

import com.myblog.yu.bean.ArticleInfoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/25 10:07
 */
@Component
public interface ArticleInfoBeanRepository extends ElasticsearchRepository<ArticleInfoBean,Integer> {
    //查询方法
    Page<ArticleInfoBean> findDistinctArticleInfoBeanByArticleTitleContainingOrArticleContentContaining(String articleTitle, String articleContent, Pageable page);
}
