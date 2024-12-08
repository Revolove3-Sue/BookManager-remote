package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.service.BorrowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 借阅服务实现类
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowServiceImpl.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @Resource
    private BorrowMapper borrowMapper;

    /**
     * 获取借阅记录总数
     * @return 借阅记录总数
     */
    @Override
    public Integer getCount() {
        try {
            return borrowMapper.selectCount();
        } catch (Exception e) {
            logger.error("获取借阅记录总数失败", e);
            return 0;
        }
    }

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的借阅记录总数
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        if (params == null) {
            return 0;
        }
        try {
            return borrowMapper.selectCountBySearch(params);
        } catch (Exception e) {
            logger.error("获取搜索结果总数失败, params: {}", params, e);
            return 0;
        }
    }

    /**
     * 分页搜索借阅记录
     * @param params 搜索参数，包含分页信息
     * @return 借阅记录列表
     */
    @Override
    public List<Borrow> searchBorrowsByPage(Map<String, Object> params) {
        if (params == null) {
            return Collections.emptyList();
        }
        try {
            List<Borrow> borrows = borrowMapper.selectBySearch(params);
            formatBorrowTimes(borrows);
            return borrows;
        } catch (Exception e) {
            logger.error("分页搜索借阅记录失败, params: {}", params, e);
            return Collections.emptyList();
        }
    }

    /**
     * 添加借阅记录（包含时间格式转换）
     * @param borrow 要添加的借阅记录
     * @return 1:添加成功; 0:添加失败
     */
    @Override
    public Integer addBorrow(Borrow borrow) {
        if (borrow == null) {
            return 0;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            if (borrow.getBorrowTimeStr() != null) {
                borrow.setBorrowTime(sdf.parse(borrow.getBorrowTimeStr()));
            }
            if (borrow.getReturnTimeStr() != null) {
                borrow.setReturnTime(sdf.parse(borrow.getReturnTimeStr()));
            }
            return borrowMapper.insertSelective(borrow);
        } catch (ParseException e) {
            logger.error("解析借阅时间出错, borrow: {}", borrow, e);
            return 0;
        } catch (Exception e) {
            logger.error("添加借阅记录失败, borrow: {}", borrow, e);
            return 0;
        }
    }

     /**
     * 添加借阅记录（不进行时间格式转换）
     * @param borrow 要添加的借阅记录
     * @return 1:添加成功; 0:添加失败
     */
    @Override
    public Integer addBorrow2(Borrow borrow) {
        if (borrow == null) {
            return 0;
        }
        try {
            return borrowMapper.insertSelective(borrow);
        } catch (Exception e) {
            logger.error("添加借阅记录失败, borrow: {}", borrow, e);
            return 0;
        }
    }

    /**
     * 删除借阅记录
     * @param borrow 要删除的借阅记录
     * @return 0:删除失败或图书未归还; 1:删除成功
     */
    @Override
    public Integer deleteBorrow(Borrow borrow) {
        if (borrow == null || borrow.getBorrowId() == null) {
            return 0;
        }
        try {
            // 检查图书是否已归还
            Borrow existingBorrow = borrowMapper.selectByPrimaryKey(borrow.getBorrowId());
            if (existingBorrow == null || existingBorrow.getReturnTime() == null) {
                return 0;
            }
            return borrowMapper.deleteByPrimaryKey(borrow.getBorrowId());
        } catch (Exception e) {
            logger.error("删除借阅记录失败, borrowId: {}", borrow.getBorrowId(), e);
            return 0;
        }
    }

    /**
     * 批量删除借阅记录
     * @param borrows 要删除的借阅记录列表
     * @return 成功删除的数量
     */
    @Override
    public Integer deleteBorrows(List<Borrow> borrows) {
        if (borrows == null || borrows.isEmpty()) {
            return 0;
        }
        try {
            return borrows.stream()
                    .mapToInt(this::deleteBorrow)
                    .sum();
        } catch (Exception e) {
            logger.error("批量删除借阅记录失败, borrows: {}", borrows, e);
            return 0;
        }
    }

    /**
     * 更新借阅记录（包含时间格式转换）
     * @param borrow 要更新的借阅记录
     * @return 1:更新成功; 0:更新失败
     */
    @Override
    public Integer updateBorrow(Borrow borrow) {
        if (borrow == null || borrow.getBorrowId() == null) {
            return 0;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            if (borrow.getBorrowTimeStr() != null) {
                borrow.setBorrowTime(sdf.parse(borrow.getBorrowTimeStr()));
            }
            if (borrow.getReturnTimeStr() != null) {
                borrow.setReturnTime(sdf.parse(borrow.getReturnTimeStr()));
            }
            return borrowMapper.updateByPrimaryKeySelective(borrow);
        } catch (ParseException e) {
            logger.error("解析更新时间出错, borrow: {}", borrow, e);
            return 0;
        } catch (Exception e) {
            logger.error("更新借阅记录失败, borrow: {}", borrow, e);
            return 0;
        }
    }

    /**
     * 更新借阅记录（不进行时间格式转换）
     * @param borrow 要更新的借阅记录
     * @return 1:更新成功; 0:更新失败
     */
    @Override
    public Integer updateBorrow2(Borrow borrow) {
        if (borrow == null || borrow.getBorrowId() == null) {
            return 0;
        }
        try {
            return borrowMapper.updateByPrimaryKeySelective(borrow);
        } catch (Exception e) {
            logger.error("更新借阅记录失败, borrow: {}", borrow, e);
            return 0;
        }
    }

    /**
     * 根据ID查询借阅记录
     * @param borrowId 借阅记录ID
     * @return 借阅记录信息
     */
    @Override
    public Borrow queryBorrowsById(Integer borrowId) {
        if (borrowId == null) {
            return null;
        }
        try {
            return borrowMapper.selectByPrimaryKey(borrowId);
        } catch (Exception e) {
            logger.error("查询借阅记录失败, borrowId: {}", borrowId, e);
            return null;
        }
    }

    /**
     * 格式化借阅时间
     * @param borrows 借阅记录列表
     */
    private void formatBorrowTimes(List<Borrow> borrows) {
        if (borrows == null || borrows.isEmpty()) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        for (Borrow borrow : borrows) {
            if (borrow.getBorrowTime() != null) {
                borrow.setBorrowTimeStr(sdf.format(borrow.getBorrowTime()));
            }
            if (borrow.getReturnTime() != null) {
                borrow.setReturnTimeStr(sdf.format(borrow.getReturnTime()));
            }
        }
    }
}
