package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户数据访问层接口
 */
public interface UserMapper {
    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     * @return 受影响的行数
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 插入新用户记录（使用所有字段）
     * @param record 用户对象
     * @return 受影响的行数
     */
    int insert(User record);

    /**
     * 插入新用户记录（只使用非空字段）
     * @param record 用户对象
     * @return 受影响的行数
     */
    int insertSelective(User record);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户对象
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 更新用户信息（只更新非空字段）
     * @param record 用户对象
     * @return 受影响的行数
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新用户信息（更新所有字段）
     * @param record 用户对象
     * @return 受影响的行数
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据用户名、密码和管理员标志查询用户
     * @param userName 用户名
     * @param password 密码
     * @param isAdmin 是否为管理员
     * @return 用户对象
     */
    User selectByuserNameAndPasswordAndisAdmin(@Param("userName") String userName,
                                             @Param("password") String password,
                                             @Param("isAdmin") Byte isAdmin);

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    User selectByuserName(String userName);

    /**
     * 分页查询用户列表
     * @param begin 起始位置
     * @param size 每页大小
     * @return 用户列表
     */
    List<User> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 查询用户总数
     * @return 用户总数
     */
    Integer selectCount();

    /**
     * 查询所有用户
     * @return 所有用户列表
     */
    List<User> selectAll();

    /**
     * 根据搜索条件查询用户数量
     * @param params 搜索参数
     * @return 符合条件的用户数量
     */
    int selectCountBySearch(Map<String, Object> params);

    /**
     * 根据搜索条件查询用户列表
     * @param params 搜索参数
     * @return 符合条件的用户列表
     */
    List<User> selectBySearch(Map<String, Object> params);
}
