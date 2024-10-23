package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.service.BookTypeService;
import com.rabbiter.bms.mapper.BookTypeMapper;
import com.rabbiter.bms.model.BookType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Override
    public Integer getCount() {
        return bookTypeMapper.selectCount();
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookTypeMapper.selectCountBySearch(params);
    }

    @Override
    public List<BookType> searchBookTypesByPage(Map<String, Object> params) {
        return bookTypeMapper.selectBySearch(params);
    }

    @Override
    public Integer addBookType(BookType bookType) {
        return bookTypeMapper.insertSelective(bookType);
    }

    @Override
    public Integer deleteBookType(BookType bookType) {
        int count = 0;
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("booktypeid", bookType.getBooktypeid());
            if(bookInfoMapper.selectCountByType(map) > 0) {
                return -1;
            }
            count = bookTypeMapper.deleteByPrimaryKey(bookType.getBooktypeid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer deleteBookTypes(List<BookType> bookTypes) {
        int count = 0;
        for(BookType bookType : bookTypes) {
            count += deleteBookType(bookType);
        }
        return count;
    }

    @Override
    public Integer updateBookType(BookType bookType) {
        return bookTypeMapper.updateByPrimaryKeySelective(bookType);
    }

    @Override
    public List<BookType> queryBookTypes() {
        return bookTypeMapper.selectAll();
    }
}
