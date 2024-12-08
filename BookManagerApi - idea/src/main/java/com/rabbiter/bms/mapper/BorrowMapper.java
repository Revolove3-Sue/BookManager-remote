package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 借阅记录数据访问接口
 */
@Mapper
public interface BorrowMapper {
    
    /**
     * 根据主键删除借阅记录
     * @param borrowId 借阅记录ID
     * @return 影响的行数
     */
    int deleteByPrimaryKey(Integer borrowId);

    /**
     * 插入完整的借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    int insert(Borrow record);

    /**
     * 插入非空的借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    int insertSelective(Borrow record);

    /**
     * 根据主键查询借阅记录
     * @param borrowId 借阅记录ID
     * @return 借阅记录对象
     */
    Borrow selectByPrimaryKey(Integer borrowId);

    /**
     * 根据主键更新非空的借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    int updateByPrimaryKeySelective(Borrow record);

    /**
     * 根据主键更新完整的借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    int updateByPrimaryKey(Borrow record);

    /**
     * 分页查询借阅记录
     * @param begin 起始位置
     * @param size 每页大小
     * @return 借阅记录列表
     */
    List<Borrow> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 获取借阅记录总数
     * @return 借阅记录总数
     */
    Integer selectCount();

    /**
     * 查询所有借阅记录
     * @return 借阅记录列表
     */
    List<Borrow> selectAll();

    /**
     * 根据搜索条件获取借阅记录总数
     * @param searchParam 搜索参数，可包含：
     *                   - userId: 用户ID
     *                   - bookId: 图书ID
     *                   - userName: 用户名
     *                   - bookName: 图书名
     *                   - borrowTime: 借阅时间
     *                   - returnTime: 归还时间
     * @return 符合条件的借阅记录总数
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询借阅记录
     * @param searchParam 搜索参数，可包含：
     *                   - begin: 起始位置
     *                   - size: 每页大小
     *                   - userId: 用户ID
     *                   - bookId: 图书ID
     *                   - userName: 用户名
     *                   - bookName: 图书名
     *                   - borrowTime: 借阅时间
     *                   - returnTime: 归还时间
     * @return 借阅记录列表
     */
    List<Borrow> selectBySearch(Map<String, Object> searchParam);

    /**
     * 根据用户ID查询未归还的借阅记录数
     * @param userId 用户ID
     * @return 未归还的借阅记录数
     */
    int selectNotReturnedCount(@Param("userId") Integer userId);

    /**
     * 根据图书ID查询未归还的借阅记录数
     * @param bookId 图书ID
     * @return 未归还的借阅记录数
     */
    int selectBookBorrowedCount(@Param("bookId") Integer bookId);
}