package com.xlw.demo.controller;

import com.alibaba.fastjson.JSON;
import com.xlw.demo.persist.customized.entity.BalanceEntity;
import com.xlw.demo.persist.customized.entity.SellerBalanceEntity;
import com.xlw.demo.service.BalanceService;
import com.xlw.demo.util.MyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @GetMapping(value = "/test")
    public String test(@RequestParam String path) {
        log.info("处理数据开始");
        //1.清空数据
        log.info("清空数据开始");
        balanceService.deleteAll();
        log.info("清空数据完毕");

        //2.保存处理数据
//        List<BalanceEntity> balanceList = FileUtil.getBalanceList("D:\\YE2022030136551.txt");
        List<BalanceEntity> balanceList = MyFileUtil.getBalanceList(path);
//        for (BalanceEntity balanceEntity : balanceList) {
//            balanceService.insertBalance(balanceEntity);
//        }

        balanceService.batchInsertBalance(balanceList);

        log.info("保存处理数据成功.处理总条数【{}】", ObjectUtils.isEmpty(balanceList) ? 0 : balanceList.size());
        //3.查询结果
        List<SellerBalanceEntity> resultList = balanceService.querySellerBalance();
        return JSON.toJSONString(resultList);
    }
}
