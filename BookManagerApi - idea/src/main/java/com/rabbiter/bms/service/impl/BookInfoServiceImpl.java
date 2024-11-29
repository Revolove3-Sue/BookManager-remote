package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.model.BookInfo;
import com.rabbiter.bms.service.BookInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class BookInfoServiceImpl implements BookInfoService {

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Resource
    private BorrowMapper borrowMapper;

    @Override
    public Integer getCount() {
        return bookInfoMapper.selectCount();
    }

    @Override
    public List<BookInfo> queryBookInfos() {
        return bookInfoMapper.selectAll();
    }

    @Override
    public BookInfo queryBookInfoById(Integer bookId) {
        return bookInfoMapper.selectByPrimaryKey(bookId);
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookInfoMapper.selectCountBySearch(params);
    }

    @Override
    public List<BookInfo> searchBookInfosByPage(Map<String, Object> params) {
        return bookInfoMapper.selectBySearch(params);
    }

    @Override
    public Integer addBookInfo(BookInfo bookInfo) {
        return bookInfoMapper.insertSelective(bookInfo);
    }

    @Override
    public Integer deleteBookInfo(BookInfo bookInfo) {
        int count = 0;
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("bookId", bookInfo.getBookId());
            if(borrowMapper.selectCountBySearch(map) > 0) {
                return -1;
            }
            count = bookInfoMapper.deleteByPrimaryKey(bookInfo.getBookId());
        } catch (Exception e) {
            log.error("删除图书信息失败", e);
        }
        return count;
    }

    @Override
    public Integer deleteBookInfos(List<BookInfo> bookInfos) {
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            count += deleteBookInfo(bookInfo);
        }
        return count;
    }

    @Override
    public Integer updateBookInfo(BookInfo bookInfo) {
        return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }

}
