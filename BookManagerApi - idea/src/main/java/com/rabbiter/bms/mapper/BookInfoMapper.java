package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 图书信息数据访问接口
 */
@Mapper
public interface BookInfoMapper {
    
    /**
     * 根据主键删除图书
     * @param bookId 图书ID
     * @return 影响的行数
     */
    int deleteByPrimaryKey(Integer bookId);

    /**
     * 插入完整的图书信息
     * @param record 图书信息对象
     * @return 影响的行数
     */
    int insert(BookInfo record);

    /**
     * 插入非空的图书信息
     * @param record 图书信息对象
     * @return 影响的行数
     */
    int insertSelective(BookInfo record);

    /**
     * 根据主键查询图书
     * @param bookId 图书ID
     * @return 图书信息对象
     */
    BookInfo selectByPrimaryKey(Integer bookId);

    /**
     * 根据主键更新非空的图书信息
     * @param record 图书信息对象
     * @return 影响的行数
     */
    int updateByPrimaryKeySelective(BookInfo record);

    /**
     * 根据主键更新完整的图书信息
     * @param record 图书信息对象
     * @return 影响的行数
     */
    int updateByPrimaryKey(BookInfo record);

    /**
     * 分页查询图书信息
     * @param begin 起始位置
     * @param size 每页大小
     * @return 图书信息列表
     */
    List<BookInfo> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 获取图书总数
     * @return 图书总数量
     */
    Integer selectCount();

    /**
     * 根据搜索条件获取图书总数
     * @param searchParam 搜索参数，可包含：
     *                   - bookName: 图书名称
     *                   - bookAuthor: 作者
     *                   - bookTypeId: 图书类型ID
     * @return 符合条件的图书总数
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询图书
     * @param searchParam 搜索参数，可包含：
     *                   - begin: 起始位置
     *                   - size: 每页大小
     *                   - bookName: 图书名称
     *                   - bookAuthor: 作者
     *                   - bookTypeId: 图书类型ID
     * @return 图书信息列表
     */
    List<BookInfo> selectBySearch(Map<String, Object> searchParam);

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    List<BookInfo> selectAll();

    /**
     * 根据图书类型统计图书数量
     * @param map 包含bookTypeId的参数Map
     * @return 该类型的图书数量
     */
    int selectCountByType(Map<String, Object> map);

    /**
     * 根据图书类型查询图书
     * @param map 包含：
     *           - bookTypeId: 图书类型ID
     *           - begin: 起始位置
     *           - size: 每页大小
     * @return 图书信息列表
     */
    List<BookInfo> selectByType(Map<String, Object> map);
}
