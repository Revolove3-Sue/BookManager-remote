package com.rabbiter.bms.service;

import com.rabbiter.bms.model.BookInfo;

import java.util.List;
import java.util.Map;

/**
 * 图书信息服务接口
 */
public interface BookInfoService {
    
    /**
     * 获取图书总数
     * @return 图书总数量
     */
    Integer getCount();

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    List<BookInfo> queryBookInfos();

    /**
     * 根据图书ID查询图书信息
     * @param bookId 图书ID
     * @return 图书信息，如果未找到返回null
     */
    BookInfo queryBookInfoById(Integer bookId);

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的图书总数
     */
    Integer getSearchCount(Map<String, Object> params);

    /**
     * 分页搜索图书信息
     * @param params 搜索参数，包含分页信息
     * @return 图书信息列表
     */
    List<BookInfo> searchBookInfosByPage(Map<String, Object> params);

    /**
     * 添加图书信息
     * @param bookInfo 要添加的图书信息
     * @return 1:添加成功; 0:添加失败
     */
    Integer addBookInfo(BookInfo bookInfo);

    /**
     * 删除图书信息
     * @param bookInfo 要删除的图书信息
     * @return -1:图书正在借阅中不能删除; 0:删除失败; 1:删除成功
     */
    Integer deleteBookInfo(BookInfo bookInfo);

    /**
     * 批量删除图书信息
     * @param bookInfos 要删除的图书信息列表
     * @return 成功删除的数量
     */
    Integer deleteBookInfos(List<BookInfo> bookInfos);

    /**
     * 更新图书信息
     * @param bookInfo 要更新的图书信息
     * @return 1:更新成功; 0:更新失败
     */
    Integer updateBookInfo(BookInfo bookInfo);
}
