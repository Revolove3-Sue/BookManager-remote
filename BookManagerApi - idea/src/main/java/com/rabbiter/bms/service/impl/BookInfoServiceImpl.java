package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.model.BookInfo;
import com.rabbiter.bms.service.BookInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 图书信息服务实现类
 */
@Service
@Slf4j 
public class BookInfoServiceImpl implements BookInfoService {

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Resource
    private BorrowMapper borrowMapper;

    /**
     * 获取图书总数
     * @return 图书总数量
     */
    @Override
    public Integer getCount() {
        try {
            return bookInfoMapper.selectCount();
        } catch (Exception e) {
            log.error("获取图书总数失败", e);
            return 0;
        }
    }

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> queryBookInfos() {
        try {
            return bookInfoMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有图书信息失败", e);
            return Collections.emptyList();
        }
    }

   /**
     * 根据图书ID查询图书信息
     * @param bookId 图书ID
     * @return 图书信息，如果未找到返回null
     */
    @Override
    public BookInfo queryBookInfoById(Integer bookId) {
        if (bookId == null) {
            return null;
        }
        try {
            return bookInfoMapper.selectByPrimaryKey(bookId);
        } catch (Exception e) {
            log.error("根据ID查询图书信息失败, bookId: {}", bookId, e);
            return null;
        }
    }

    /**
     * 获取搜索结果的总数
     * @param params 搜索参数
     * @return 符合条件的图书总数
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        if (params == null) {
            return 0;
        }
        try {
            return bookInfoMapper.selectCountBySearch(params);
        } catch (Exception e) {
            log.error("获取搜索结果总数失败, params: {}", params, e);
            return 0;
        }
    }

   /**
     * 分页搜索图书信息
     * @param params 搜索参数，包含分页信息
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> searchBookInfosByPage(Map<String, Object> params) {
        if (params == null) {
            return Collections.emptyList();
        }
        try {
            return bookInfoMapper.selectBySearch(params);
        } catch (Exception e) {
            log.error("分页搜索图书信息失败, params: {}", params, e);
            return Collections.emptyList();
        }
    }

   /**
     * 添加图书信息
     * @param bookInfo 要添加的图书信息
     * @return 1:添加成功; 0:添加失败
     */
    @Override
    public Integer addBookInfo(BookInfo bookInfo) {
        if (bookInfo == null) {
            return 0;
        }
        try {
            return bookInfoMapper.insertSelective(bookInfo);
        } catch (Exception e) {
            log.error("添加图书信息失败, bookInfo: {}", bookInfo, e);
            return 0;
        }
    }

    /**
     * 删除图书信息
     * @param bookInfo 要删除的图书信息
     * @return -1:图书正在借阅中不能删除; 0:删除失败; 1:删除成功
     */
    @Override
    public Integer deleteBookInfo(BookInfo bookInfo) {
        try {
            if (bookInfo == null || bookInfo.getBookId() == null) {
                return 0;
            }
            
            Map<String, Object> map = new HashMap<>();
            map.put("bookId", bookInfo.getBookId());
            
            // 检查图书是否被借出
            if (borrowMapper.selectCountBySearch(map) > 0) {
                return -1;
            }
            
            return bookInfoMapper.deleteByPrimaryKey(bookInfo.getBookId());
        } catch (Exception e) {
            log.error("删除图书信息失败, bookId: {}", bookInfo.getBookId(), e);
            return 0;
        }
    }

    /**
     * 批量删除图书信息
     * @param bookInfos 要删除的图书信息列表
     * @return 成功删除的数量
     */
    @Override
    public Integer deleteBookInfos(List<BookInfo> bookInfos) {
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            count += deleteBookInfo(bookInfo);
        }
        return count;
    }

    /**
     * 更新图书信息
     * @param bookInfo 要更新的图书信息
     * @return 1:更新成功; 0:更新失败
     */
    @Override
    public Integer updateBookInfo(BookInfo bookInfo) {
        if (bookInfo == null || bookInfo.getBookId() == null) {
            return 0;
        }
        try {
            return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
        } catch (Exception e) {
            log.error("更新图书信息失败, bookInfo: {}", bookInfo, e);
            return 0;
        }
    }
}
