package com.myblog.yu.service;

import com.myblog.yu.bean.MessageInfo;

import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/22 23:00
 */
public interface MessageService {

    /**
     * 查询留言信息
     * @param info
     * @return
     */
    public List<MessageInfo> getList(MessageInfo info);

    /**
     * 修改在线留言信息
     * @param info
     * @return
     */
    public boolean update(MessageInfo info);

    /**
     * 删除留言信息
     * @param messageId
     * @return
     */
    public boolean delete(Integer messageId);

    /**
     * 前台，展示需要留言信息
     * @return
     */
    public List<MessageInfo> getMessagesList();

    /**
     * 前台，在线添加留言
     */
    public boolean add(MessageInfo info);
}
