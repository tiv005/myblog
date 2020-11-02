package com.myblog.yu.dao;

import com.myblog.yu.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户管理的数据访问接口
 * select * from user_info LIMIT 2,3
 */
@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    /**
     * 根据编号查询用户信息
     * @param userId
     * @return
     */
    UserInfo selectByPrimaryKey(Integer userId);

    /**
     * 按照条件修改用户
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * 修改用户要修改全部数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserInfo record);

    /**
     * 根据分页信息查询用户信息
     * @param user 查询条件
     * @return  返回多个用户信息
     */
    public List<UserInfo> getUserList(UserInfo user);

    /**
     * 根据条件查询用户的数据（根据这个数量来进行分页）
     * @param user 查询条件
     * @return  返回用户的数量
     */
    public Long getUserCount(UserInfo user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    public UserInfo isLogin(UserInfo user);

}