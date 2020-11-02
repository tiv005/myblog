package com.myblog.yu.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 *
 * Document 加上这个注解后，默认的情况下这个实体中所有属性都会被建立索引，并且分词
 *  indexName 索引库的名称，建议以项目命名
 *  type 类型  建议以实体的名称命名
 *  shards 分区数
 *  replicas 分区的备份数
 *  refreshInterval 刷新时间
 *
 * @author 容
 * @version 1.0
 * @date 2020/7/25 9:56
 */
@Document(indexName = "megacorp2",type = "ArticleInfoBean",shards = 1,replicas = 0,refreshInterval = "-1")
public class ArticleInfoBean {

    @Id
    private Integer articleId;

    @Field
    private String articleTitle;

    @Field
    private String articleContent;

    @Field
    private String articleImg;

    @Field
    private Date articleTime;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public Date getArticleTime() {
        return articleTime;
    }

    public void setArticleTime(Date articleTime) {
        this.articleTime = articleTime;
    }

}
