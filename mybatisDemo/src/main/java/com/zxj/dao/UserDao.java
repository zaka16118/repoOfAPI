package com.zxj.dao;

import com.zxj.pojo.User;

import java.util.List;

public interface UserDao {
    /**
     * 查找全部用户
     * @return
     */
    List<User> getUserList();

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);

    int deleteUserById(int id);
}
