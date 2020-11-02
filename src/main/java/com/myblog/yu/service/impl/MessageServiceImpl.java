package com.myblog.yu.service.impl;

import com.myblog.yu.bean.MessageInfo;
import com.myblog.yu.dao.MessageInfoMapper;
import com.myblog.yu.service.MessageService;
import com.myblog.yu.utils.Const;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/22 23:02
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageInfoMapper messageInfoMapper;
    /**
     * 查询留言信息
     * @param info
     * @return
     */
    @Override
    public List<MessageInfo> getList(MessageInfo info) {
        return messageInfoMapper.getMessageList(info);
    }

    /**
     * 修改在线留言信息
     * @param info
     * @return
     */
    @Override
    public boolean update(MessageInfo info) {

        try {
            int count = messageInfoMapper.updateByPrimaryKeySelective(info);
            if (count>0){
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据编号删除留言信息
     * @param messageId
     * @return
     */
    @Override
    public boolean delete(Integer messageId) {
        try{
            int count = messageInfoMapper.deleteByPrimaryKey(messageId);
            if(count>0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 前台，展示需要留言信息
     * @return
     */
    @Override
    public List<MessageInfo> getMessagesList() {
       return messageInfoMapper.getMessagesList();
    }

    /**
     * 前台，在线添加留言
     * @param info
     * @return
     */
    @Override
    public boolean add(MessageInfo info) {
        try {
            if (info!=null){
                info.setMessageTime(new Date());
                info.setMessageMark(Const.MESSAGE_MARK);
            }
            int count = messageInfoMapper.insertSelective(info);
            if (count>0){
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
