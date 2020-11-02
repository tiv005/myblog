package com.myblog.yu.utils;

import java.io.Serializable;

/**
 * 所有实体类的父类（需要分页的情况下）
 * select * from user_info LIMIT 2,3
 * limit start ,length
 * @author 容
 * @version 1.0
 * @date 2020/7/15 22:27
 */
public class BaseBean implements Serializable {

    /**
     * 起始下标
     */
    private Integer start;

    /**
     * 查询的个数
     */
    private Integer Length;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return Length;
    }

    public void setLength(Integer length) {
        Length = length;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "start=" + start +
                ", Length=" + Length +
                '}';
    }
}
