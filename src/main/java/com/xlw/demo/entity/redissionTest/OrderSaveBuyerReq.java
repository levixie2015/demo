package com.xlw.demo.entity.redissionTest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSaveBuyerReq {
    /**
     * 订单主项信息
     */
    private OrderInfo orderInfo;

    /**
     * 订单子项信息
     */
    private List<OrderDetailInfo> orderDetailInfoList;

    /**
     * 订单支付信息
     */
    private List<OrderPayInfoInfo> orderPayInfoList;

    /**
     * 订单优惠信息
     */
    private List<OrderDiscountInfo> orderDiscountInfoList;

    private String userId;


}
