package com.xlw.demo.service;

import com.xlw.demo.persist.customized.entity.BalanceEntity;
import com.xlw.demo.persist.customized.entity.SellerBalanceEntity;

import java.util.List;

public interface BalanceService {
    /**
     * 新增
     *
     * @param entity 余额对象
     */
    void insertBalance(BalanceEntity entity);

    /**
     * 批量新增
     *
     * @param entityList 余额对象集合
     */
    void batchInsertBalance(List<BalanceEntity> entityList);

    /**
     * 查询卖家子账户余额
     *
     * @return
     */
    List<SellerBalanceEntity> querySellerBalance();

    void deleteAll();
}
