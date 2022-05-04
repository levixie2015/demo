package com.xlw.demo.entity.redissionTest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderInfo {

    private static final long serialVersionUID = 1L;

    ////@ApiModelProperty(value = "主键ID")
    private String id;

    ////@ApiModelProperty(value = "卖家ID")
    private String sellerId;

    ////@ApiModelProperty(value = "卖家名称")
    private String sellerName;

    ////@ApiModelProperty(value = "买家ID")
    private String buyerId;

    // //@ApiModelProperty(value = "买家名称")
    private String buyerName;

    // //@ApiModelProperty(value = "销售员ID")
    private String salePersonId;

    // //@ApiModelProperty(value = "销售员名称")
    private String salePersonName;

    ////@ApiModelProperty(value = "销售部门code")
    private String saleDepCode;

    // //@ApiModelProperty(value = "销售部门名称")
    private String saleDepName;

    // //@ApiModelProperty(value = "销售岗位code")
    private String salePosiCode;

    ////@ApiModelProperty(value = "销售岗位名称")
    private String salePosiName;

    // //@ApiModelProperty(value = "订单编号")
    private String orderCode;

    // //@ApiModelProperty(value = "sap订单编号")
    private String sapOrderCode;

    ////@ApiModelProperty(value = "商品总金额")
    private java.math.BigDecimal totalProductAmount;

    // //@ApiModelProperty(value = "物流运费总金额")
    private java.math.BigDecimal totalFreightAmount;

    ////@ApiModelProperty(value = "优惠总金额")
    private java.math.BigDecimal totalDiscountAmount;

    // //@ApiModelProperty(value = "承兑手续费")
    private java.math.BigDecimal acceptanceCharges;

    // //@ApiModelProperty(value = "尾差标示")
    private String tailFlag;

    // //@ApiModelProperty(value = "尾差商品总金额")
    private java.math.BigDecimal tailTotalProductAmount;

    // //@ApiModelProperty(value = "尾差物流运费总金额")
    private java.math.BigDecimal tailTotalFreightAmount;

    ////@ApiModelProperty(value = "尾差优惠总金额")
    private java.math.BigDecimal tailTotalDiscountAmount;

    // //@ApiModelProperty(value = "尾差订单总金额")
    private java.math.BigDecimal tailTotalAmount;

    // //@ApiModelProperty(value = "重量单位编号")
    private String weightUnitCode;

    // //@ApiModelProperty(value = "重量单位名称")
    private String weightUnit;

    // //@ApiModelProperty(value = "订单总数量(购买数量)")
    private BigDecimal orderTotalNum;

    //@ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    //@ApiModelProperty(value = "订单状态")
    private String orderStatus;

    //@ApiModelProperty(value = "订单状态")
    private List<String> orderStatusList;

    //@ApiModelProperty(value = "订单类型")
    private String orderType;

    //@ApiModelProperty(value = "订单来源（APP;PC）")
    private String orderOrigin;

    //@ApiModelProperty(value = "下单来源(一键购；购物车)")
    private String singleWay;

    //@ApiModelProperty(value = "支付有效期")
    private Integer payValidityNum;

    //@ApiModelProperty(value = "确认有效期")
    private Integer confirmValiditySum;

    //@ApiModelProperty(value = "支付有效期")
    private Date payValidityDate;

    //@ApiModelProperty(value = "支付有效期APP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payValidityDateApp;

    //@ApiModelProperty(value = "确认有效期")
    private Date confirmValidityDate;

    //@ApiModelProperty(value = "确认有效期APP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmValidityDateApp;

    //@ApiModelProperty(value = "币种")
    private String currency;

    //@ApiModelProperty(value = "已发货数量")
    private BigDecimal deliveryNum;

    //@ApiModelProperty(value = "剩余发货数量")
    private BigDecimal surplusDeliveryNum;

    //@ApiModelProperty(value = "商品所在地（总发货地址）")
    private String productSite;

    //@ApiModelProperty(value = "实际完成数量")
    private BigDecimal actualCompleteNum;

    //@ApiModelProperty(value = "sap订单生效标示")
    private String sapOrderFlag;

    //@ApiModelProperty(value = "运输方式")
    private String deliveryMode;

    //@ApiModelProperty(value = "配送方式")
    private String logisticsMode;

    //@ApiModelProperty(value = "收货地址(下单选择)")
    private String consigneeAddress;

    //@ApiModelProperty(value = "收货人名称")
    private String consigneeName;

    //@ApiModelProperty(value = "收货人电话")
    private String consigneeTel;

    //@ApiModelProperty(value = "提货有效时间")
    private Integer deliveryValidityNum;

    //@ApiModelProperty(value = "提货有效期")
    private Date deliveryValidityDate;

    //@ApiModelProperty(value = "发货时间")
    private Date deliveryDate;

    //@ApiModelProperty(value = "发货省份代码")
    private String deliveryProvincesCode;

    //@ApiModelProperty(value = "发货城市代码")
    private String deliveryCitiesCode;

    //@ApiModelProperty(value = "发货地区代码")
    private String deliveryRegionsCode;

    //@ApiModelProperty(value = "发货省份")
    private String deliveryProvinces;

    //@ApiModelProperty(value = "发货城市")
    private String deliveryCities;

    //@ApiModelProperty(value = "发货地区")
    private String deliveryRegions;

    //@ApiModelProperty(value = "发货经度")
    private String deliveryLongitude;

    //@ApiModelProperty(value = "发货纬度")
    private String deliveryLatitude;

    //@ApiModelProperty(value = "发货经纬度")
    private String deliveryLongitudeLatitude;

    //@ApiModelProperty(value = "发货详细地址")
    private String deliveryDetailAddress;

    //@ApiModelProperty(value = "合同编号")
    private String contractCode;

    //@ApiModelProperty(value = "合同名称")
    private String contractName;

    //@ApiModelProperty(value = "合同生效时间")
    private Date contractValidDate;

    //@ApiModelProperty(value = "挂牌id")
    private String publishId;

    //@ApiModelProperty(value = "订单确认时间")
    private Date orderComfirmDate;

    //@ApiModelProperty(value = "订单确认人ID")
    private String orderComfirmUserId;

    //@ApiModelProperty(value = "订单确认人")
    private String orderComfirmUser;

    //@ApiModelProperty(value = "订单支付时间")
    private Date orderPayDate;

    //@ApiModelProperty(value = "订单支付人id")
    private String orderPayUserId;

    //@ApiModelProperty(value = "订单支付人")
    private String orderPayUser;

    //@ApiModelProperty(value = "订单完成时间")
    private Date orderCompleteDate;

    //@ApiModelProperty(value = "订单完成人id")
    private String orderCompleteUserId;

    //@ApiModelProperty(value = "订单完成人")
    private String orderCompleteUser;

    //@ApiModelProperty(value = "备注")
    private String remark;

    //@ApiModelProperty(value = "当前有效状态")
    private String aliveFlag;

    //@ApiModelProperty(value = "创建时间")
    private Date createDate;

    //@ApiModelProperty(value = "创建者ID")
    private String createUser;

    //@ApiModelProperty(value = "创建者名称")
    private String createUserName;

    //@ApiModelProperty(value = "创建人岗位")
    private String postCode;

    //@ApiModelProperty(value = "创建人部门")
    private String departmentCode;

    //@ApiModelProperty(value = "更新时间")
    private Date updateDate;

    //@ApiModelProperty(value = "更新者ID")
    private String updateUser;

    //@ApiModelProperty(value = "更新者名称")
    private String updateUserName;

    //@ApiModelProperty(value = "预留字段1-jde")
    private String extend1;

    //@ApiModelProperty(value = "预留字段2-jde")
    private String extend2;

    /**
     * @see com.sinofert.service.transaction.api.constant.SellerTypeEnum
     */
    //@ApiModelProperty(value = "预留字段3 - 卖家类型(10:自营,20:三方)")
    private String extend3;

    //@ApiModelProperty(value = "订单强制完成原因")
    private String forceCompleteReason;

    //@ApiModelProperty(value = "订单强制完成标识（1是；0否）")
    private String forceCompleteFlag;

    //@ApiModelProperty(value = "发货类型编码（10: 厂家直销、20: 库存销售）")
    private String deliveryTypeCode;

    //@ApiModelProperty(value = "发货类型名称")
    private String deliveryTypeName;

    //@ApiModelProperty(value = "是否还有关联派车单（1是；0否）")
    private String isRelationFlag;

    /******查询追加字段 ****start*********************************************************/
    private String productName;//商品名称
    private String createDateStart;//订单开始时间
    private String createDateEnd;//订单结束时间

    private String saleOrganName;//企业名称：销售机构名称

    private String oldOrderId; // 逆向订单原订单ID
    private String oldOrderCode; // 逆向订单原订单编号
    private BigDecimal sellerSignforNum; // 卖家确认签收数量
    private BigDecimal sellerSignforAmount; // 卖家确认签收金额
    private String buyerReceiveAddressId; // 买家收货地址ID

    /******查询追加字段 ****end*********************************************************/

    /******结果集追加字段***start**********************************************************/
    private BigDecimal listingPrice;
    /**
     * 挂牌单价
     */
    private BigDecimal productPrice;
    /**
     * 商品单价
     */
    private BigDecimal productNumber;
    /**
     * 商品数量
     */
    private String productSpec;
    /**
     * 商品规格
     */
    private String productId;
    /**
     * 商品规格
     */
    private String payType;
    /**
     * 支付类型
     */

    private String orderStatusName;
    /**
     * 订单状态中文
     */
    private String deliveryModeName;
    /**
     * 运输方式中文
     */
    private String logisticsModeName;
    /**
     * 配送方式中文
     */
    private String payTypeName;
    /**
     * 支付方式
     */
    private String transactionTypeName;
    /**
     * 交易类型
     */
    private String priceClauseNo;
    /**
     * 价格条款编码
     */
    private String priceClauseName;
    /**
     * 价格条款名称
     */
    private String orderTypeName;
    /**
     * 订单类型中文
     */
    private String paymentTypeName;/**收款方式中文*/

    /******结果集追加字段***end**********************************************************/

    /******支付追加字段***start**********************************************************/
    private String payStatus;
    /**
     * 支付中心返回状态
     */
    private String payDesc;
    /**
     * 支付中心支付失败错误原因
     */
    private String financeNo;
    /**
     * 资金流水号
     */
    private String payAllowFlag;
    /**
     * 是否允许支付标识（1允许、0不允许）
     */
    private String payAllowMsg;/**是否允许支付文字消息*/
    /******支付追加字段***end**********************************************************/

    //@ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateApp;

    //@ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    //@ApiModelProperty(value = "取消订单标识 true是")
    private Boolean isCancel;

    //@ApiModelProperty(value = "是否为挂牌商品 10是 20否")
    private String isPublish;

    //代客下单列表查询是需要传 20代客下单、30代客下单非挂牌商品两个type
    //@ApiModelProperty(value = "订单类型集合")
    private List<String> orderTypes;

    //@ApiModelProperty(value = "金融业务类型（0：保税仓；1：非金融业务）")
    private String financialBusinessType;

    //@ApiModelProperty(value = "铁运火车站点ID")
    private String railwayStationId;

    //@ApiModelProperty(value = "铁运到站省份名称")
    private String railwayStationProvinceName;

    //@ApiModelProperty(value = "铁运到站工厂名称")
    private String railwayStationFactoryName;

    //@ApiModelProperty(value = "铁运到达车站名称")
    private String railwayStationName;

    //@ApiModelProperty(value = "铁运专用线描述")
    private String railwaySpecialUseLineDesc;

    //@ApiModelProperty(value = "铁运国税号")
    private String railwayNationalTaxNo;

    //@ApiModelProperty(value = "铁运收货人身份证号")
    private String railwayReceiverIdCard;

    //@ApiModelProperty(value = "铁运收货单位名称")
    private String railwayReceivingUnitName;

    //@ApiModelProperty(value = "铁运小票邮寄地址")
    private String railwayReceiptPostAddress;

    // 重写setter方法
    public void setPayValidityDate(Date payValidityDate) {
        this.payValidityDate = payValidityDate;
        this.payValidityDateApp = payValidityDate;
    }

    public void setConfirmValidityDate(Date confirmValidityDate) {
        this.confirmValidityDate = confirmValidityDate;
        this.confirmValidityDateApp = confirmValidityDate;
    }

    /******结果集追加字段***end**********************************************************/

    /**
     * 卖家确认签收数量
     */
    private String sellerSignForNum1;

    /**
     * 待签收数量
     */
    private String toBeSignForNum;

    /**
     * 付款确认（肥易贷）：PaymentConfirmEnum.java
     */
    private Integer paymentConfirm;

    //@ApiModelProperty(value = "收款方式")
    private String paymentType;

    //@ApiModelProperty(value = "竞拍ID")
    private String auId;

    //@ApiModelProperty(value = "商品图片-文件全路径")
    private String fullPath;

    //@ApiModelProperty(value = "订单取消说明")
    private String cancelOrderRemark;

    //@ApiModelProperty(value = "销售员手机号")
    private String salePersonMobile;

    //@ApiModelProperty(value = "挂牌-配送方式")
    private String publishLogisticsMode;

    //@ApiModelProperty(value = "挂牌-运输方式")
    private String publishDeliveryMode;

    //@ApiModelProperty(value = "商品来源 0:JDE 1:ERP")
    private String goodsFrom;

    //@ApiModelProperty(value = "是否补单订单：10：是 20：否")
    private String reissuedType;

    //@ApiModelProperty(value = "补单对应的历史订单code")
    private String historyOrderCode;

    //@ApiModelProperty(value = "销售区域CODE")
    private String saleAreaCode;

    //@ApiModelProperty(value = "买家ID集合")
    private List<String> buyerIds;

    //@ApiModelProperty(value = "货源")
    private String sourceOfGoods;

    //@ApiModelProperty(value = "物料编码")
    private String materialCode;

    //@ApiModelProperty(value = "物料名称")
    private String materialName;

    //@ApiModelProperty(value = "订单数量限制-大于等于")
    private BigDecimal orderTotalNumLimit;
}