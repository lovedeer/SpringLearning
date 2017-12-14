package com.smart.dao;

import com.smart.domain.User;

import java.util.List;

/**
 * User对象Dao
 */
public interface UserDao{

    /**
     * 根据用户名查询User对象
     *
     * @param userName 用户名
     * @return 对应userName的User对象，如果不存在，返回null。
     */
    User getUserByUserName(String userName);

    /**
     * 根据用户名为模糊查询条件，查询出所有前缀匹配的User对象
     *
     * @param userName 用户名查询条件
     * @return 用户名前缀匹配的所有User对象
     */
    List<User> queryUserByUserName(String userName);

    List<User> queryAllUsers();
    void save(User t);

    void update(User t);

    void remove(User t);

    User get(int objectId);

}
