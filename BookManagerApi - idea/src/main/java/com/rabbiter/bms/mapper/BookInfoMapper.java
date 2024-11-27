package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.BookInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookInfoMapper {
    int deleteByPrimaryKey(Integer bookId);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    BookInfo selectByPrimaryKey(Integer bookId);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKey(BookInfo record);

    List<BookInfo> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    Integer selectCount();

    int selectCountBySearch(Map<String, Object> searchParam);

    List<BookInfo> selectBySearch(Map<String, Object> searchParam);

    List<BookInfo> selectAll();

    int selectCountByType(Map<String, Object> map);

    List<BookInfo> selectByType(Map<String, Object> map);
}
