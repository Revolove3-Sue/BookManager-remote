package com.rabbiter.bms.web;

import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.UserService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.utils.TokenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 * 处理用户相关的所有请求，包括登录、注册、信息管理等
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user 包含用户名和密码的用户对象
     * @return 登录结果，包含token信息
     */
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestBody User user) {
        User userObj = userService.login(user);
        if (userObj == null) {
            return MyResult.getResultMap(420, "账号或密码错误");
        }
        
        String token = TokenProcessor.getInstance().makeToken();
        userService.saveUser(token, userObj);
        return MyResult.getResultMap(200, "登录成功",
                new HashMap<String, String>() {{ put("token", token); }});
    }

    /**
     * 获取用户信息
     * @param token 用户登录令牌
     * @return 用户信息
     */
    @RequestMapping(value = "/info")
    public Map<String, Object> info(String token) {
        User user = userService.getUser(token);
        return user == null ? 
            MyResult.getResultMap(420, "获取用户信息失败") :
            MyResult.getResultMap(200, "获取用户信息成功", user);
    }

    /**
     * 用户退出登录
     * @param token 用户登录令牌
     * @return 退出结果
     */
    @RequestMapping(value = "/logout")
    public Map<String, Object> logout(String token) {
        userService.removeUser(token);
        return MyResult.getResultMap(200, "退出登录成功");
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param password 密码
     * @return 注册结果：1成功，0失败
     */
    @RequestMapping(value = "/register")
    public Integer register(String userName, String password) {
        return userService.register(userName, password);
    }

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param userName 用户名
     * @param isAdmin 是否管理员
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果：1成功，0失败
     */
    @RequestMapping(value = {"/alterPassword", "reader/alterPassword"})
    public Integer alterPassword(Integer userId, String userName, Byte isAdmin, 
                               String oldPassword, String newPassword) {
        User userObj = new User();
        userObj.setUserId(userId);
        userObj.setUserName(userName);
        userObj.setUserPassword(oldPassword);
        userObj.setIsAdmin(isAdmin);

        User user = userService.login(userObj);
        if (user == null) {
            return 0;
        }
        userService.setPassword(userObj.getUserId(), newPassword);
        return 1;
    }

    /**
     * 获取用户总数
     * @return 用户总数
     */
    @GetMapping(value = "/getCount")
    public Integer getCount() {
        return userService.getCount();
    }

    /**
     * 查询所有用户
     * @return 用户列表
     */
    @GetMapping(value = "/queryUsers")
    public List<User> queryUsers() {
        return userService.queryUsers();
    }

    /**
     * 分页查询用户
     * @param params 包含page、limit、userName等参数的Map
     * @return 分页结果
     */
    @GetMapping(value = "/queryUsersByPage")
    public Map<String, Object> queryUsersByPage(@RequestParam Map<String, Object> params) {
        MyUtils.parsePageParams(params);
        int count = userService.getSearchCount(params);
        List<User> users = userService.searchUsersByPage(params);
        return MyResult.getListResultMap(0, "success", count, users);
    }

    /**
     * 添加新用户
     * @param user 用户信息
     * @return 添加结果：1成功，0失败
     */
    @PostMapping(value = "/addUser")
    public Integer addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 删除单个用户
     * @param user 要删除的用户信息
     * @return 删除结果：1成功，0失败
     */
    @DeleteMapping(value = "/deleteUser")
    public Integer deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }

    /**
     * 批量删除用户
     * @param users 要删除的用户列表
     * @return 删除结果：1成功，0失败
     */
    @DeleteMapping(value = "/deleteUsers")
    public Integer deleteUsers(@RequestBody List<User> users) {
        return userService.deleteUsers(users);
    }

    /**
     * 更新用户信息
     * @param user 更新的用户信息
     * @return 更新结果：1成功，0失败
     */
    @RequestMapping(value = "/updateUser")
    public Integer updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
