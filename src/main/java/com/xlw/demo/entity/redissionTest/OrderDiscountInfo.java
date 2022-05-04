package com.xlw.demo.entity.redissionTest;

import lombok.Data;


@Data
public class OrderDiscountInfo {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "主键ID")
    private String discountId;

    //@ApiModelProperty(value = "卖家ID")
    private String sellerId;

    //@ApiModelProperty(value = "订单ID")
    private String orderId;

    //@ApiModelProperty(value = "订单子项id")
    private String orderDetailId;

    //@ApiModelProperty(value = "优惠类型（3）")
    private String discountType;

    //@ApiModelProperty(value = "优惠金额")
    private java.math.BigDecimal discountMoney;

    //@ApiModelProperty(value = "状态")
    private String status;

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

    //@ApiModelProperty(value = "优惠类型名称")
    private String discountTypeName;

}