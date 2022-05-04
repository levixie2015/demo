package com.xlw.demo.entity.redissionTest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Author: cy
 */

@Data
public class OrderPayInfoInfo {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "主键ID")
    private String payId;

    //@ApiModelProperty(value = "卖家ID")
    private String sellerId;

    //@ApiModelProperty(value = "订单ID")
    private String orderId;

    //@ApiModelProperty(value = "支付状态")
    private String payStatus;

    //@ApiModelProperty(value = "支付类型")
    private String payType;

    //@ApiModelProperty(value = "支付时间")
    private java.util.Date payDate;

    //@ApiModelProperty(value = "承兑手续费")
    private java.math.BigDecimal acceptanceCharges;

    //@ApiModelProperty(value = "支付金额")
    private java.math.BigDecimal payAmount;

    //@ApiModelProperty(value = "sap收款单号")
    private String sapReceiptCode;

    //@ApiModelProperty(value = "sap冻结单号")
    private String sapFreezeCode;

    //@ApiModelProperty(value = "备注")
    private String remark;

    //@ApiModelProperty(value = "当前有效状态")
    private String aliveFlag;

    //@ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    //@ApiModelProperty(value = "创建者ID")
    private String createUser;

    //@ApiModelProperty(value = "创建者名称")
    private String createUserName;

    //@ApiModelProperty(value = "更新时间")
    private java.util.Date updateDate;

    //@ApiModelProperty(value = "更新者ID")
    private String updateUser;

    //@ApiModelProperty(value = "更新者名称")
    private String updateUserName;

    //@ApiModelProperty(value = "预留字段1")
    private String extend1;

    //@ApiModelProperty(value = "预留字段2")
    private String extend2;

    //@ApiModelProperty(value = "预留字段3")
    private String extend3;

    //@ApiModelProperty(value = "支付状态名称")
    private String payStatusName;
    //@ApiModelProperty(value = "支付类型名称")
    private String payTypeName;

    //@ApiModelProperty(value = "支付时间app")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date payDateApp;

}