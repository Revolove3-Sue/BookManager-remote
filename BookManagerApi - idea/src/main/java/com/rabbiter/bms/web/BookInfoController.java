package com.rabbiter.bms.web;

import com.rabbiter.bms.service.BookInfoService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.model.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 图书信息控制器
 * 处理图书相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/bookInfo")
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    /**
     * 获取图书总数
     * @return 图书总数量
     */
    @GetMapping("/getCount")
    public Integer getCount() {
        try {
            return bookInfoService.getCount();
        } catch (Exception e) {
            log.error("获取图书总数失败", e);
            return 0;
        }
    }

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    @GetMapping("/queryBookInfos")
    public List<BookInfo> queryBookInfos() {
        try {
            return bookInfoService.queryBookInfos();
        } catch (Exception e) {
            log.error("查询所有图书信息失败", e);
            return null;
        }
    }

    /**
     * 分页搜索查询图书信息
     * @param params 查询参数，包含：
     *              - page: 页码
     *              - limit: 每页大小
     *              - bookName: 图书名称（可选）
     *              - bookAuthor: 作者（可选）
     *              - bookTypeId: 图书类型ID（可选）
     * @return 包含查询结果的Map：
     *         - code: 状态码（0表示成功）
     *         - msg: 提示信息
     *         - count: 总记录数
     *         - data: 图书信息列表
     */
    @GetMapping("/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params) {
        try {
            MyUtils.parsePageParams(params);
            int count = bookInfoService.getSearchCount(params);
            List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);
            return MyResult.getListResultMap(0, "success", count, bookInfos);
        } catch (Exception e) {
            log.error("分页查询图书信息失败", e);
            return MyResult.getListResultMap(1, "查询失败：" + e.getMessage(), 0, null);
        }
    }

    /**
     * 添加图书信息
     * @param bookInfo 图书信息对象
     * @return 1:添加成功; 0:添加失败
     */
    @PostMapping("/addBookInfo")
    public Integer addBookInfo(@RequestBody BookInfo bookInfo) {
        try {
            return bookInfoService.addBookInfo(bookInfo);
        } catch (Exception e) {
            log.error("添加图书信息失败", e);
            return 0;
        }
    }

    /**
     * 删除图书信息
     * @param bookInfo 要删除的图书信息
     * @return 1:删除成功; 0:删除失败; -1:图书已借出，不能删除
     */
    @DeleteMapping("/deleteBookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo) {
        try {
            return bookInfoService.deleteBookInfo(bookInfo);
        } catch (Exception e) {
            log.error("删除图书信息失败", e);
            return 0;
        }
    }

    /**
     * 批量删除图书信息
     * @param bookInfos 要删除的图书信息列表
     * @return 成功删除的数量
     */
    @DeleteMapping("/deleteBookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos) {
        try {
            return bookInfoService.deleteBookInfos(bookInfos);
        } catch (Exception e) {
            log.error("批量删除图书信息失败", e);
            return 0;
        }
    }

    /**
     * 更新图书信息
     * @param bookInfo 要更新的图书信息
     * @return 1:更新成功; 0:更新失败
     */
    @PutMapping("/updateBookInfo")
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo) {
        try {
            return bookInfoService.updateBookInfo(bookInfo);
        } catch (Exception e) {
            log.error("更新图书信息失败", e);
            return 0;
        }
    }
}
