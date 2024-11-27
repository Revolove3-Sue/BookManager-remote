package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.service.BorrowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    private BorrowMapper borrowMapper;

    @Override
    public Integer getCount() {
        return borrowMapper.selectCount();
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return borrowMapper.selectCountBySearch(params);
    }

    @Override
    public List<Borrow> searchBorrowsByPage(Map<String, Object> params) {
        List<Borrow> borrows = borrowMapper.selectBySearch(params);
        // 添加string类型的时间显示
        for(Borrow borrow : borrows) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(borrow.getBorrowTime() != null) borrow.setBorrowTimeStr(simpleDateFormat.format(borrow.getBorrowTime()));
            if(borrow.getReturnTime() != null) borrow.setReturnTimeStr(simpleDateFormat.format(borrow.getReturnTime()));
        }
        return borrows;
    }

    @Override
    public Integer addBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowTime(simpleDateFormat.parse(borrow.getBorrowTimeStr()));
            borrow.setReturnTime(simpleDateFormat.parse(borrow.getReturnTimeStr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.insertSelective(borrow);
    }

    // 不会调整时间格式的add
    @Override
    public Integer addBorrow2(Borrow borrow) {
        return borrowMapper.insertSelective(borrow);
    }

    @Override
    public Integer deleteBorrow(Borrow borrow) {
        // 先查询有没有还书
        Borrow borrow1 = borrowMapper.selectByPrimaryKey(borrow.getBorrowId());
        if(borrow1.getReturnTime() == null) return 0;
        return borrowMapper.deleteByPrimaryKey(borrow.getBorrowId());
    }

    @Override
    public Integer deleteBorrows(List<Borrow> borrows) {
        int count = 0;
        for(Borrow borrow : borrows) {
            count += deleteBorrow(borrow);
        }
        return count;
    }

    @Override
    public Integer updateBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowTime(simpleDateFormat.parse(borrow.getBorrowTimeStr()));
            borrow.setReturnTime(simpleDateFormat.parse(borrow.getReturnTimeStr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.updateByPrimaryKeySelective(borrow);
    }

    // 不调整时间格式的更新
    @Override
    public Integer updateBorrow2(Borrow borrow) {
        return borrowMapper.updateByPrimaryKeySelective(borrow);
    }

    @Override
    public Borrow queryBorrowsById(Integer borrowId) {
        return borrowMapper.selectByPrimaryKey(borrowId);
    }

}
