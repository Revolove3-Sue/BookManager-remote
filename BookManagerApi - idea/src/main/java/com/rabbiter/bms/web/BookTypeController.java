package com.rabbiter.bms.web;

import com.rabbiter.bms.model.BookType;
import com.rabbiter.bms.service.BookTypeService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 图书类型控制器
 * 处理图书类型相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/bookType")
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    /**
     * 获取图书类型总数
     * @return 图书类型总数
     */
    @GetMapping("/getCount")
    public Integer getCount() {
        try {
            return bookTypeService.getCount();
        } catch (Exception e) {
            log.error("获取图书类型总数失败", e);
            return 0;
        }
    }

    /**
     * 查询所有图书类型
     * 支持读者和管理员访问
     * @return 图书类型列表
     */
    @GetMapping(value = {"/queryBookTypes", "/reader/queryBookTypes"})
    public List<BookType> queryBookTypes() {
        try {
            return bookTypeService.queryBookTypes();
        } catch (Exception e) {
            log.error("查询所有图书类型失败", e);
            return null;
        }
    }

    /**
     * 分页查询图书类型
     * @param params 查询参数，包含：
     *              - page: 页码
     *              - limit: 每页大小
     *              - bookTypeName: 类型名称（可选）
     * @return 包含查询结果的Map：
     *         - code: 状态码（0表示成功）
     *         - msg: 提示信息
     *         - count: 总记录数
     *         - data: 图书类型列表
     */
    @GetMapping("/queryBookTypesByPage")
    public Map<String, Object> queryBookTypesByPage(@RequestParam Map<String, Object> params) {
        try {
            MyUtils.parsePageParams(params);
            int count = bookTypeService.getSearchCount(params);
            List<BookType> bookTypes = bookTypeService.searchBookTypesByPage(params);
            return MyResult.getListResultMap(0, "success", count, bookTypes);
        } catch (Exception e) {
            log.error("分页查询图书类型失败", e);
            return MyResult.getListResultMap(1, "查询失败：" + e.getMessage(), 0, null);
        }
    }

    /**
     * 添加图书类型
     * @param bookType 图书类型对象
     * @return 1:添加成功; 0:添加失败
     */
    @PostMapping("/addBookType")
    public Integer addBookType(@RequestBody BookType bookType) {
        try {
            return bookTypeService.addBookType(bookType);
        } catch (Exception e) {
            log.error("添加图书类型失败", e);
            return 0;
        }
    }

    /**
     * 删除图书类型
     * @param bookType 要删除的图书类型
     * @return 1:删除成功; 0:删除失败; -1:该类型下有图书，不能删除
     */
    @DeleteMapping("/deleteBookType")
    public Integer deleteBookType(@RequestBody BookType bookType) {
        try {
            return bookTypeService.deleteBookType(bookType);
        } catch (Exception e) {
            log.error("删除图书类型失败", e);
            return 0;
        }
    }

    /**
     * 批量删除图书类型
     * @param bookTypes 要删除的图书类型列表
     * @return 成功删除的数量
     */
    @DeleteMapping("/deleteBookTypes")
    public Integer deleteBookTypes(@RequestBody List<BookType> bookTypes) {
        try {
            return bookTypeService.deleteBookTypes(bookTypes);
        } catch (Exception e) {
            log.error("批量删除图书类型失败", e);
            return 0;
        }
    }

    /**
     * 更新图书类型
     * @param bookType 要更新的图书类型
     * @return 1:更新成功; 0:更新失败
     */
    @PutMapping("/updateBookType")
    public Integer updateBookType(@RequestBody BookType bookType) {
        try {
            return bookTypeService.updateBookType(bookType);
        } catch (Exception e) {
            log.error("更新图书类型失败", e);
            return 0;
        }
    }
}
