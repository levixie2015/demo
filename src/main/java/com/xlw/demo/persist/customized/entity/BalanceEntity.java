package com.xlw.demo.persist.customized.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * txt格式: 每一行：序号&交易网会员代码&子账户&可用金额&
 */
@Data
public class BalanceEntity implements Serializable {
    /**
     * 序号
     */
    private String sort;
    /**
     * 交易网会员代码
     */
    private String tranNetCode;
    /**
     * 子账户
     */
    private String subAcctNo;
    /**
     * 可用金额
     */
    private BigDecimal amount;
    /**
     * 公司id
     */
    private String companyId;
}
