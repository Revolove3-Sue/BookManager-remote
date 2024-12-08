package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private BorrowMapper borrowMapper;

    /**
     * 用户登录
     * @param user 包含用户名、密码和管理员标识的用户对象
     * @return 登录成功返回用户信息，失败返回null
     */
    @Override
    public User login(User user) {
        if (user == null || user.getUserName() == null || user.getUserPassword() == null) {
            return null;
        }
        try {
            return userMapper.selectByuserNameAndPasswordAndisAdmin(
                user.getUserName(), 
                user.getUserPassword(), 
                user.getIsAdmin()
            );
        } catch (Exception e) {
            log.error("用户登录失败, user: {}", user, e);
            return null;
        }
    }

    /**
     * 将用户信息保存到Redis
     * @param token 用户令牌
     * @param user 用户信息
     */
    @Override
    public void saveUser(String token, User user) {
        if (token == null || user == null) {
            return;
        }
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("保存用户信息到Redis失败, token: {}, user: {}", token, user, e);
        }
    }

    /**
     * 从Redis获取用户信息
     * @param token 用户令牌
     * @return 用户信息
     */
    @Override
    public User getUser(String token) {
        if (token == null) {
            return null;
        }
        try {
            return (User) redisTemplate.opsForValue().get(token);
        } catch (Exception e) {
            log.error("从Redis获取用户信息失败, token: {}", token, e);
            return null;
        }
    }

   /**
     * 从Redis删除用户信息
     * @param token 用户令牌
     */
    @Override
    public void removeUser(String token) {
        if (token == null) {
            return;
        }
        try {
            redisTemplate.delete(token);
        } catch (Exception e) {
            log.error("从Redis删除用户信息失败, token: {}", token, e);
        }
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param password 密码
     * @return 0:用户名已存在; 1:注册成功; -1:注册失败
     */
    @Override
    public Integer register(String userName, String password) {
        if (userName == null || password == null) {
            return -1;
        }
        try {
            User existingUser = userMapper.selectByuserName(userName);
            if (existingUser != null) {
                return 0;
            }

            User user = new User();
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setIsAdmin((byte)0);
            return userMapper.insertSelective(user);
        } catch (Exception e) {
            log.error("用户注册失败, userName: {}", userName, e);
            return -1;
        }
    }

    /**
     * 修改用户密码
     * @param id 用户ID
     * @param password 新密码
     */
    @Override
    public void setPassword(Integer id, String password) {
        if (id == null || password == null) {
            return;
        }
        try {
            User user = new User();
            user.setUserId(id);
            user.setUserPassword(password);
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("修改用户密码失败, userId: {}", id, e);
        }
    }

    /**
     * 获取用户总数
     * @return 用户总数
     */
    @Override
    public Integer getCount() {
        try {
            return userMapper.selectCount();
        } catch (Exception e) {
            log.error("获取用户总数失败", e);
            return 0;
        }
    }

    /**
     * 查询所有用户
     * @return 用户列表
     */
    @Override
    public List<User> queryUsers() {
        try {
            return userMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有用户失败", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的用户总数
     */
    @Override
    public int getSearchCount(Map<String, Object> params) {
        if (params == null) {
            return 0;
        }
        try {
            return userMapper.selectCountBySearch(params);
        } catch (Exception e) {
            log.error("获取搜索结果总数失败, params: {}", params, e);
            return 0;
        }
    }

    /**
     * 分页搜索用户
     * @param params 搜索参数，包含分页信息
     * @return 用户列表
     */
    @Override
    public List<User> searchUsersByPage(Map<String, Object> params) {
        if (params == null) {
            return Collections.emptyList();
        }
        try {
            return userMapper.selectBySearch(params);
        } catch (Exception e) {
            log.error("分页搜索用户失败, params: {}", params, e);
            return Collections.emptyList();
        }
    }

    /**
     * 添加用户
     * @param user 要添加的用户信息
     * @return 1:添加成功; 0:添加失败
     */
    @Override
    public Integer addUser(User user) {
        if (user == null) {
            return 0;
        }
        try {
            return userMapper.insertSelective(user);
        } catch (Exception e) {
            log.error("添加用户失败, user: {}", user, e);
            return 0;
        }
    }

    /**
     * 删除用户
     * @param user 要删除的用户
     * @return -2:不能删除管理员; -1:用户有未归还的图书; 1:删除成功; 0:删除失败
     */
    @Override
    public Integer deleteUser(User user) {
        if (user == null || user.getUserId() == null) {
            return 0;
        }
        try {
            if (user.getUserId() == 1) {
                return -2;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getUserId());
            if (borrowMapper.selectCountBySearch(map) > 0) {
                return -1;
            }
            return userMapper.deleteByPrimaryKey(user.getUserId());
        } catch (Exception e) {
            log.error("删除用户失败, userId: {}", user.getUserId(), e);
            return 0;
        }
    }

    /**
     * 批量删除用户
     * @param users 要删除的用户列表
     * @return 成功删除的数量
     */
    @Override
    public Integer deleteUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            return 0;
        }
        try {
            return users.stream()
                    .mapToInt(this::deleteUser)
                    .sum();
        } catch (Exception e) {
            log.error("批量删除用户失败, users: {}", users, e);
            return 0;
        }
    }

    /**
     * 更新用户信息
     * @param user 要更新的用户信息
     * @return 1:更新成功; 0:更新失败
     */
    @Override
    public Integer updateUser(User user) {
        if (user == null || user.getUserId() == null) {
            return 0;
        }
        try {
            return userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("更新用户信息失败, user: {}", user, e);
            return 0;
        }
    }
}
