package com.rabbiter.bms.service;

import com.rabbiter.bms.model.BookType;

import java.util.List;
import java.util.Map;

/**
 * 图书类型服务接口
 */
public interface BookTypeService {
    
    /**
     * 获取图书类型总数
     * @return 图书类型总数量
     */
    Integer getCount();

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的图书类型总数
     */
    Integer getSearchCount(Map<String, Object> params);

    /**
     * 分页搜索图书类型
     * @param params 搜索参数，包含分页信息
     * @return 图书类型列表
     */
    List<BookType> searchBookTypesByPage(Map<String, Object> params);

    /**
     * 添加图书类型
     * @param bookType 要添加的图书类型信息
     * @return 1:添加成功; 0:添加失败
     */
    Integer addBookType(BookType bookType);

    /**
     * 删除图书类型
     * @param bookType 要删除的图书类型
     * @return -1:该类型下有图书，不能删除; 0:删除失败; 1:删除成功
     */
    Integer deleteBookType(BookType bookType);

    /**
     * 批量删除图书类型
     * @param bookTypes 要删除的图书类型列表
     * @return 成功删除的数量
     */
    Integer deleteBookTypes(List<BookType> bookTypes);

    /**
     * 更新图书类型信息
     * @param bookType 要更新的图书类型信息
     * @return 1:更新成功; 0:更新失败
     */
    Integer updateBookType(BookType bookType);

    /**
     * 查询所有图书类型
     * @return 图书类型列表
     */
    List<BookType> queryBookTypes();
}
