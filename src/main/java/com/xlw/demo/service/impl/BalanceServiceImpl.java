package com.xlw.demo.service.impl;

import com.xlw.demo.persist.customized.entity.BalanceEntity;
import com.xlw.demo.persist.customized.entity.SellerBalanceEntity;
import com.xlw.demo.persist.customized.mapper.BalanceExtMapper;
import com.xlw.demo.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class BalanceServiceImpl implements BalanceService {
    @Resource
    private BalanceExtMapper balanceExtMapper;

    @Override
    public void insertBalance(BalanceEntity entity) {
        balanceExtMapper.insert(entity);
    }

    @Override
    public void batchInsertBalance(List<BalanceEntity> entityList) {
        balanceExtMapper.batchInsertBalance(entityList);
    }

    @Override
    public List<SellerBalanceEntity> querySellerBalance() {
        return balanceExtMapper.querySellerBalance();
    }

    @Override
    public void deleteAll() {
        balanceExtMapper.deleteAll();
    }
}
