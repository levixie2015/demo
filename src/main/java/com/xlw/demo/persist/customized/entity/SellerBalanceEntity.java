package com.xlw.demo.persist.customized.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SellerBalanceEntity implements Serializable {
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 子账户可用金额
     */
    private BigDecimal amount;
}
