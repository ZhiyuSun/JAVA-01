package com.wsbo.readwriteseparate1.dal.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Trade {
    private Integer id;

    private String tradeId;

    private String uid;

    private String status;

    private Date payTime;

    private BigDecimal totalAmount;

    private BigDecimal receivedAmount;

    private Date createTime;

    private Date modifyTime;

    private String goodsInfo;

    private String failMsg;
}