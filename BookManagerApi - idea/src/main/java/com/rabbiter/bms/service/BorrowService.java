package com.rabbiter.bms.service;

import com.rabbiter.bms.model.Borrow;

import java.util.List;
import java.util.Map;

/**
 * 借阅服务接口
 * 提供图书借阅相关的业务操作
 */
public interface BorrowService {
    
    /**
     * 获取借阅记录总数
     * @return 借阅记录总数
     */
    Integer getCount();

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的借阅记录总数
     */
    Integer getSearchCount(Map<String, Object> params);

    /**
     * 分页搜索借阅记录
     * @param params 搜索参数，包含分页信息
     * @return 借阅记录列表
     */
    List<Borrow> searchBorrowsByPage(Map<String, Object> params);

    /**
     * 添加借阅记录（包含时间格式转换）
     * @param borrow 要添加的借阅记录
     * @return 1:添加成功; 0:添加失败
     */
    Integer addBorrow(Borrow borrow);

    /**
     * 添加借阅记录（不进行时间格式转换）
     * @param borrow 要添加的借阅记录
     * @return 1:添加成功; 0:添加失败
     */
    Integer addBorrow2(Borrow borrow);

    /**
     * 删除借阅记录
     * @param borrow 要删除的借阅记录
     * @return 0:删除失败或图书未归还; 1:删除成功
     */
    Integer deleteBorrow(Borrow borrow);

    /**
     * 批量删除借阅记录
     * @param borrows 要删除的借阅记录列表
     * @return 成功删除的数量
     */
    Integer deleteBorrows(List<Borrow> borrows);

    /**
     * 更新借阅记录（包含时间格式转换）
     * @param borrow 要更新的借阅记录
     * @return 1:更新成功; 0:更新失败
     */
    Integer updateBorrow(Borrow borrow);

    /**
     * 更新借阅记录（不进行时间格式转换）
     * @param borrow 要更新的借阅记录
     * @return 1:更新成功; 0:更新失败
     */
    Integer updateBorrow2(Borrow borrow);

    /**
     * 根据ID查询借阅记录
     * @param borrowId 借阅记录ID
     * @return 借阅记录信息，如果未找到返回null
     */
    Borrow queryBorrowsById(Integer borrowId);
}
