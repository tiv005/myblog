package com.myblog.yu.dao;

import com.myblog.yu.bean.MessageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageInfoMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    MessageInfo selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(MessageInfo record);

    int updateByPrimaryKey(MessageInfo record);

    /**
     * 根据条件查询留言信息
     * @param info
     * @return
     */
    public List<MessageInfo> getMessageList(MessageInfo info);

    /**
     * 前台，展示留言信息
     * @return
     */
    public List<MessageInfo> getMessagesList();
}