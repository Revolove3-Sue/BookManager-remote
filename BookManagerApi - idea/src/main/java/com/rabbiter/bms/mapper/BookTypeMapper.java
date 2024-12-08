package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.BookType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 图书类型数据访问接口
 */
@Mapper
public interface BookTypeMapper {
    
    /**
     * 根据主键删除图书类型
     * @param bookTypeId 图书类型ID
     * @return 影响的行数
     */
    int deleteByPrimaryKey(Integer bookTypeId);

    /**
     * 插入完整的图书类型信息
     * @param record 图书类型对象
     * @return 影响的行数
     */
    int insert(BookType record);

    /**
     * 插入非空的图书类型信息
     * @param record 图书类型对象
     * @return 影响的行数
     */
    int insertSelective(BookType record);

    /**
     * 根据主键查询图书类型
     * @param bookTypeId 图书类型ID
     * @return 图书类型对象
     */
    BookType selectByPrimaryKey(Integer bookTypeId);

    /**
     * 根据主键更新非空的图书类型信息
     * @param record 图书类型对象
     * @return 影响的行数
     */
    int updateByPrimaryKeySelective(BookType record);

    /**
     * 根据主键更新完整的图书类型信息
     * @param record 图书类型对象
     * @return 影响的行数
     */
    int updateByPrimaryKey(BookType record);

    /**
     * 分页查询图书类型
     * @param begin 起始位置
     * @param size 每页大小
     * @return 图书类型列表
     */
    List<BookType> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 获取图书类型总数
     * @return 图书类型总数
     */
    Integer selectCount();

    /**
     * 查询所有图书类型
     * @return 图书类型列表
     */
    List<BookType> selectAll();

    /**
     * 根据搜索条件获取图书类型总数
     * @param searchParam 搜索参数，可包含：
     *                   - bookTypeName: 类型名称
     *                   - bookTypeDesc: 类型描述
     * @return 符合条件的图书类型总数
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询图书类型
     * @param searchParam 搜索参数，可包含：
     *                   - begin: 起始位置
     *                   - size: 每页大小
     *                   - bookTypeName: 类型名称
     *                   - bookTypeDesc: 类型描述
     * @return 图书类型列表
     */
    List<BookType> selectBySearch(Map<String, Object> searchParam);
}
