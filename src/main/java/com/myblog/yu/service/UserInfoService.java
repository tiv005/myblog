package com.myblog.yu.service;

import com.myblog.yu.bean.UserInfo;
import com.myblog.yu.utils.PageBean;

import java.util.List;

/**
 * 用户管理业务逻辑接口
 * @author 容
 * @version 1.0
 * @date 2020/7/16 16:36
 */
public interface UserInfoService {
    /**
     * 根据分页条件查询用户的信息
     * @param user
     * @param page
     * @return
     */
    public PageBean<UserInfo> getList(UserInfo user, Integer page);

    /**
     * 添加用户信息
     * @param user 用户信息
     * @return
     */
    public boolean add(UserInfo user);

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    public UserInfo getUser(UserInfo user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean update(UserInfo user);

    /**
     * 删除用户信息，假删除，只是改变标识状态
     * @param user
     * @return
     */
    public boolean delete(UserInfo user);


    /**
     * 根据账号和密码查询用户信息
     * @param user
     * @return
     */
    public UserInfo isLogin(UserInfo user);

    /**
     * 获取用户的总数
     * @param
     * @return
     */
    /**
     * 获取用户的个数
     * @return
     */
    public int getCount();
}
