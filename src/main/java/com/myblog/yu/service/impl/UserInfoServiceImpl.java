package com.myblog.yu.service.impl;

import com.myblog.yu.bean.UserInfo;
import com.myblog.yu.dao.UserInfoMapper;
import com.myblog.yu.service.UserInfoService;
import com.myblog.yu.utils.Const;
import com.myblog.yu.utils.PageBean;
import com.myblog.yu.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/16 16:39
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 通过用户信息通过分页展示出来
     * @param user
     * @param page
     * @return
     */
    @Override
    public PageBean<UserInfo> getList(UserInfo user, Integer page) {
        //总记录数
        int allRow = userInfoMapper.getUserCount(user).intValue();
        //总页数
        int totalPage = PageUtils.countTotalPage(allRow, Const.PAGE_SIZE);
        //当前第几页
        int currentPage = PageUtils.countCurrentPage(page);
        //起始记录数
        int start = PageUtils.countStart(Const.PAGE_SIZE,currentPage);

        if(page>=0){
            user.setStart(start);
            user.setLength(Const.PAGE_SIZE);
        }else {
            user.setStart(-1);
            user.setStart(-1);
        }

        List<UserInfo> users = userInfoMapper.getUserList(user);

        PageBean<UserInfo> pageBean = new PageBean<>();
        pageBean.setAllRow(allRow);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        pageBean.setList(users);

        return pageBean;
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean add(UserInfo user) {
        try {
            if(user!=null){
                user.setUserMark(Const.MARK_YES);
            }
            int count = userInfoMapper.insertSelective(user);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据编号查询用户信息
     * @param user
     * @return
     */
    @Override
    public UserInfo getUser(UserInfo user) {
        if(user!=null && user.getUserId()!=null){
            return userInfoMapper.selectByPrimaryKey(user.getUserId());
        }
        return null;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Override
    public boolean update(UserInfo user) {
        try {
            if(user!=null){
                user.setUserMark(Const.MARK_YES);
            }
            int count =  userInfoMapper.updateByPrimaryKeySelective(user);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(UserInfo user) {
        try {
            user.setUserMark(Const.MARK_NO);
            int count =  userInfoMapper.updateByPrimaryKeySelective(user);
            if (count>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserInfo isLogin(UserInfo user) {
        try{
            return userInfoMapper.isLogin(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return userInfoMapper.getUserCount(null).intValue();
    }
}
