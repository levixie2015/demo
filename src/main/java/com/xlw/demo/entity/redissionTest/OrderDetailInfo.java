package com.xlw.demo.entity.redissionTest;

import lombok.Data;

@Data
public class OrderDetailInfo {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "主键ID")
    private String detailId;

    //@ApiModelProperty(value = "订单ID")
    private String orderId;

    //@ApiModelProperty(value = "商品名称")
    private String productName;

    //@ApiModelProperty(value = "物料CODE")
    private String materialCode;

    //@ApiModelProperty(value = "商品ID")
    private String productId;

    //@ApiModelProperty(value = "重量单位")
    private String weightUnit;

    //@ApiModelProperty(value = "挂牌单价")
    private java.math.BigDecimal listingPrice;

    //@ApiModelProperty(value = "商品单价")
    private java.math.BigDecimal productPrice;

    //@ApiModelProperty(value = "商品数量")
    private java.math.BigDecimal productNumber;

    //@ApiModelProperty(value = "商品规格")
    private String productSpec;

    //@ApiModelProperty(value = "商品用途")
    private String commodityuse;

    //@ApiModelProperty(value = "商品用途名称")
    private String commodityuseName;

    //@ApiModelProperty(value = "价格条款编号")
    private String priceClauseNo;

    //@ApiModelProperty(value = "价格条款名称")
    private String priceClauseName;

    //@ApiModelProperty(value = "商品总金额")
    private java.math.BigDecimal productTotalAmount;

    //@ApiModelProperty(value = "预计发货周期")
    private String deliveryCycle;

    //@ApiModelProperty(value = "买家备注信息")
    private String buyerRemark;

    //@ApiModelProperty(value = "承兑手续费")
    private java.math.BigDecimal acceptanceCharges;

    //@ApiModelProperty(value = "商品大类CODE")
    private String upGoodsTypeCode;

    //@ApiModelProperty(value = "商品中类CODE")
    private String midGoodsTypeCode;

    //@ApiModelProperty(value = "商品小类CODE")
    private String downGoodsTypeCode;

    //@ApiModelProperty(value = "商品大类")
    private String upGoodsTypeName;

    //@ApiModelProperty(value = "商品中类")
    private String midGoodsTypeName;

    //@ApiModelProperty(value = "商品小类")
    private String downGoodsTypeName;

    //@ApiModelProperty(value = "库区id")
    private String depotAreaId;

    //@ApiModelProperty(value = "库区name")
    private String depotAreaName;

    //@ApiModelProperty(value = "sap子项id")
    private String sapItemId;

    //@ApiModelProperty(value = "区域id")
    private String regionId;

    //@ApiModelProperty(value = "销售机构id")
    private String saleOrganId;

    //@ApiModelProperty(value = "区域")
    private String regionName;

    //@ApiModelProperty(value = "销售机构")
    private String saleOrganName;

    //@ApiModelProperty(value = "物流运费金额")
    private java.math.BigDecimal freightAmount;

    //@ApiModelProperty(value = "优惠金额")
    private java.math.BigDecimal discountAmount;

    //@ApiModelProperty(value = "订单子项总金额")
    private java.math.BigDecimal orderItemAmount;

    //@ApiModelProperty(value = "实际完成数量")
    private java.math.BigDecimal actualCompleteNum;

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

    /**
     * 卖家ID
     */
    private String sellerId;

    /**
     * 买家ID
     */
    private String buyerId;

}