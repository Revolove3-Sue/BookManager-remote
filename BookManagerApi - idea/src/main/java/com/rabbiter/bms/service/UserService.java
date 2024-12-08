package com.rabbiter.bms.service;

import com.rabbiter.bms.model.User;

import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 * 提供用户管理、认证和授权相关的业务操作
 */
public interface UserService {

    /**
     * 用户登录
     * @param user 包含用户名、密码和管理员标识的用户对象
     * @return 登录成功返回用户信息，失败返回null
     */
    User login(User user);

    /**
     * 将用户信息保存到Redis缓存
     * @param token 用户令牌
     * @param user 用户信息
     */
    void saveUser(String token, User user);

    /**
     * 从Redis缓存获取用户信息
     * @param token 用户令牌
     * @return 用户信息，如果未找到返回null
     */
    User getUser(String token);

    /**
     * 从Redis缓存移除用户信息
     * @param token 用户令牌
     */
    void removeUser(String token);

    /**
     * 用户注册
     * @param userName 用户名
     * @param password 密码
     * @return 0:用户名已存在; 1:注册成功; -1:注册失败
     */
    Integer register(String userName, String password);

    /**
     * 修改用户密码
     * @param id 用户ID
     * @param password 新密码
     */
    void setPassword(Integer id, String password);

    /**
     * 获取用户总数
     * @return 用户总数
     */
    Integer getCount();

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> queryUsers();

    /**
     * 获取搜索结果的总数
     * @param searchParam 搜索参数
     * @return 符合条件的用户总数
     */
    int getSearchCount(Map<String, Object> searchParam);

    /**
     * 分页搜索用户
     * @param params 搜索参数，包含分页信息
     * @return 用户列表
     */
    List<User> searchUsersByPage(Map<String, Object> params);

    /**
     * 添加用户
     * @param user 要添加的用户信息
     * @return 1:添加成功; 0:添加失败
     */
    Integer addUser(User user);

    /**
     * 删除用户
     * @param user 要删除的用户
     * @return -2:不能删除管理员; -1:用户有未归还的图书; 1:删除成功; 0:删除失败
     */
    Integer deleteUser(User user);

    /**
     * 批量删除用户
     * @param users 要删除的用户列表
     * @return 成功删除的数量
     */
    Integer deleteUsers(List<User> users);

    /**
     * 更新用户信息
     * @param user 要更新的用户信息
     * @return 1:更新成功; 0:更新失败
     */
    Integer updateUser(User user);
}
