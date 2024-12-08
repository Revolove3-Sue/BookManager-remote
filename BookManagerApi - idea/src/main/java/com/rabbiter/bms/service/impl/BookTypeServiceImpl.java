package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.service.BookTypeService;
import com.rabbiter.bms.mapper.BookTypeMapper;
import com.rabbiter.bms.model.BookType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 图书类型服务实现类
 */
@Service
@Slf4j
public class BookTypeServiceImpl implements BookTypeService {

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Resource
    private BookInfoMapper bookInfoMapper;

    /**
     * 获取图书类型总数
     * @return 图书类型总数量
     */
    @Override
    public Integer getCount() {
        try {
            return bookTypeMapper.selectCount();
        } catch (Exception e) {
            log.error("获取图书类型总数失败", e);
            return 0;
        }
    }

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的图书类型总数
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        if (params == null) {
            return 0;
        }
        try {
            return bookTypeMapper.selectCountBySearch(params);
        } catch (Exception e) {
            log.error("获取搜索结果总数失败, params: {}", params, e);
            return 0;
        }
    }

    /**
     * 分页搜索图书类型
     * @param params 搜索参数，包含分页信息
     * @return 图书类型列表
     */
    @Override
    public List<BookType> searchBookTypesByPage(Map<String, Object> params) {
        if (params == null) {
            return Collections.emptyList();
        }
        try {
            return bookTypeMapper.selectBySearch(params);
        } catch (Exception e) {
            log.error("分页搜索图书类型失败, params: {}", params, e);
            return Collections.emptyList();
        }
    }

    /**
     * 添加图书类型
     * @param bookType 要添加的图书类型信息
     * @return 1:添加成功; 0:添加失败
     */
    @Override
    public Integer addBookType(BookType bookType) {
        if (bookType == null) {
            return 0;
        }
        try {
            return bookTypeMapper.insertSelective(bookType);
        } catch (Exception e) {
            log.error("添加图书类型失败, bookType: {}", bookType, e);
            return 0;
        }
    }

    /**
     * 删除图书类型
     * @param bookType 要删除的图书类型
     * @return -1:该类型下有图书，不能删除; 0:删除失败; 1:删除成功
     */
    @Override
    public Integer deleteBookType(BookType bookType) {
        if (bookType == null || bookType.getBookTypeId() == null) {
            return 0;
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bookTypeId", bookType.getBookTypeId());
            // 检查是否有图书属于该类型
            if (bookInfoMapper.selectCountByType(map) > 0) {
                return -1;
            }
            return bookTypeMapper.deleteByPrimaryKey(bookType.getBookTypeId());
        } catch (Exception e) {
            log.error("删除图书类型失败, bookTypeId: {}", bookType.getBookTypeId(), e);
            return 0;
        }
    }

    /**
     * 批量删除图书类型
     * @param bookTypes 要删除的图书类型列表
     * @return 成功删除的数量
     */
    @Override
    public Integer deleteBookTypes(List<BookType> bookTypes) {
        if (bookTypes == null || bookTypes.isEmpty()) {
            return 0;
        }
        try {
            return bookTypes.stream()
                    .mapToInt(this::deleteBookType)
                    .sum();
        } catch (Exception e) {
            log.error("批量删除图书类型失败, bookTypes: {}", bookTypes, e);
            return 0;
        }
    }

    /**
     * 更新图书类型信息
     * @param bookType 要更新的图书类型信息
     * @return 1:更新成功; 0:更新失败
     */
    @Override
    public Integer updateBookType(BookType bookType) {
        if (bookType == null || bookType.getBookTypeId() == null) {
            return 0;
        }
        try {
            return bookTypeMapper.updateByPrimaryKeySelective(bookType);
        } catch (Exception e) {
            log.error("更新图书类型失败, bookType: {}", bookType, e);
            return 0;
        }
    }

    /**
     * 查询所有图书类型
     * @return 图书类型列表
     */
    @Override
    public List<BookType> queryBookTypes() {
        try {
            return bookTypeMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有图书类型失败", e);
            return Collections.emptyList();
        }
    }
}
