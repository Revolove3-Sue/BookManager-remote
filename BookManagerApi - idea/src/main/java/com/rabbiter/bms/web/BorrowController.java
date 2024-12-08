package com.rabbiter.bms.web;

import com.rabbiter.bms.exception.NotEnoughException;
import com.rabbiter.bms.exception.OperationFailureException;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.service.BookInfoService;
import com.rabbiter.bms.service.BorrowService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.model.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 借阅管理控制器
 * 处理图书借阅相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
    
    @Autowired
    private BookInfoService bookInfoService;

    /**
     * 分页查询借阅记录
     * @param params 查询参数，包含：
     *              - page: 页码
     *              - limit: 每页大小
     *              - userId: 用户ID（可选）
     *              - bookId: 图书ID（可选）
     * @return 包含查询结果的Map
     */
    @GetMapping("/queryBorrowsByPage")
    public Map<String, Object> queryBorrowsByPage(@RequestParam Map<String, Object> params) {
        try {
            MyUtils.parsePageParams(params);
            int count = borrowService.getSearchCount(params);
            List<Borrow> borrows = borrowService.searchBorrowsByPage(params);
            return MyResult.getListResultMap(0, "success", count, borrows);
        } catch (Exception e) {
            log.error("分页查询借阅记录失败", e);
            return MyResult.getListResultMap(1, "查询失败：" + e.getMessage(), 0, null);
        }
    }

    /**
     * 添加借阅记录
     * @param borrow 借阅记录对象
     * @return 1:添加成功; 0:添加失败
     */
    @PostMapping("/addBorrow")
    public Integer addBorrow(@RequestBody Borrow borrow) {
        try {
            return borrowService.addBorrow(borrow);
        } catch (Exception e) {
            log.error("添加借阅记录失败", e);
            return 0;
        }
    }

    /**
     * 获取借阅记录总数
     * @return 借阅记录总数
     */
    @GetMapping("/getCount")
    public Integer getCount() {
        try {
            return borrowService.getCount();
        } catch (Exception e) {
            log.error("获取借阅记录总数失败", e);
            return 0;
        }
    }

    /**
     * 删除借阅记录
     * @param borrow 要删除的借阅记录
     * @return 1:删除成功; 0:删除失败
     */
    @DeleteMapping("/deleteBorrow")
    public Integer deleteBorrow(@RequestBody Borrow borrow) {
        try {
            return borrowService.deleteBorrow(borrow);
        } catch (Exception e) {
            log.error("删除借阅记录失败", e);
            return 0;
        }
    }

    /**
     * 批量删除借阅记录
     * @param borrows 要删除的借阅记录列表
     * @return 成功删除的数量
     */
    @DeleteMapping("/deleteBorrows")
    public Integer deleteBorrows(@RequestBody List<Borrow> borrows) {
        try {
            return borrowService.deleteBorrows(borrows);
        } catch (Exception e) {
            log.error("批量删除借阅记录失败", e);
            return 0;
        }
    }

    /**
     * 更新借阅记录
     * @param borrow 要更新的借阅记录
     * @return 1:更新成功; 0:更新失败
     */
    @PutMapping("/updateBorrow")
    public Integer updateBorrow(@RequestBody Borrow borrow) {
        try {
            return borrowService.updateBorrow(borrow);
        } catch (Exception e) {
            log.error("更新借阅记录失败", e);
            return 0;
        }
    }

    /**
     * 借书操作
     * 支持普通用户和管理员访问
     * @param userId 用户ID
     * @param bookId 图书ID
     * @return 1:借阅成功; 0:借阅失败
     */
    @PostMapping(value = {"/borrowBook", "/reader/borrowBook"})
    @Transactional
    public Integer borrowBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        try {
            // 查询图书情况
            BookInfo theBook = bookInfoService.queryBookInfoById(bookId);

            if (theBook == null) {
                throw new NullPointerException("图书" + bookId + "不存在");
            } else if (theBook.getIsBorrowed() == 1) {
                throw new NotEnoughException("图书" + bookId + "库存不足（已被借走）");
            }

            // 更新图书借阅状态
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookId(bookId);
            bookInfo.setIsBorrowed((byte) 1);
            if (bookInfoService.updateBookInfo(bookInfo) == 0) {
                throw new OperationFailureException("图书" + bookId + "更新借阅状态失败");
            }

            // 添加借阅记录
            Borrow borrow = new Borrow();
            borrow.setUserId(userId);
            borrow.setBookId(bookId);
            borrow.setBorrowTime(new Date());
            if (borrowService.addBorrow2(borrow) == 0) {
                throw new OperationFailureException("图书" + bookId + "添加借阅记录失败");
            }

            return 1;
        } catch (Exception e) {
            log.error("借书操作失败", e);
            return 0;
        }
    }

    /**
     * 还书操作
     * 支持普通用户和管理员访问
     * @param borrowId 借阅记录ID
     * @param bookId 图书ID
     * @return 1:还书成功; 0:还书失败
     */
    @PostMapping(value = {"/returnBook", "/reader/returnBook"})
    @Transactional
    public Integer returnBook(@RequestParam Integer borrowId, @RequestParam Integer bookId) {
        try {
            // 查询相关信息
            BookInfo theBook = bookInfoService.queryBookInfoById(bookId);
            Borrow theBorrow = borrowService.queryBorrowsById(borrowId);

            if (theBook == null) {
                throw new NullPointerException("图书" + bookId + "不存在");
            } else if (theBorrow == null) {
                throw new NullPointerException("借阅记录" + borrowId + "不存在");
            } else if (theBorrow.getReturnTime() != null) {
                throw new NotEnoughException("图书" + bookId + "已经还过了");
            }

            // 更新图书借阅状态
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookId(bookId);
            bookInfo.setIsBorrowed((byte) 0);
            if (bookInfoService.updateBookInfo(bookInfo) == 0) {
                throw new OperationFailureException("图书" + bookId + "更新借阅状态失败");
            }

            // 更新借阅记录
            Borrow borrow = new Borrow();
            borrow.setBorrowId(borrowId);
            borrow.setReturnTime(new Date());
            if (borrowService.updateBorrow2(borrow) == 0) {
                throw new OperationFailureException("图书" + bookId + "更新借阅记录失败");
            }

            return 1;
        } catch (Exception e) {
            log.error("还书操作失败", e);
            return 0;
        }
    }
}