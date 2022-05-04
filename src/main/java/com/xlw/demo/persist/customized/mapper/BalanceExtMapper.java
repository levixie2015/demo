package com.xlw.demo.persist.customized.mapper;

import com.xlw.demo.persist.customized.entity.BalanceEntity;
import com.xlw.demo.persist.customized.entity.SellerBalanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BalanceExtMapper {

    int insert(BalanceEntity entity);

    /**
     * 查询卖家子账户余额
     *
     * @return
     */
    List<SellerBalanceEntity> querySellerBalance();

    void deleteAll();

    void batchInsertBalance(@Param("list") List<BalanceEntity> list);
}