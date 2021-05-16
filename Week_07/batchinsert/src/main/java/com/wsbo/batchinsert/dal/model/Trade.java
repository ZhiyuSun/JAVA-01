package com.wsbo.batchinsert.dal.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo == null ? null : goodsInfo.trim();
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg == null ? null : failMsg.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", uid=").append(uid);
        sb.append(", status=").append(status);
        sb.append(", payTime=").append(payTime);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", receivedAmount=").append(receivedAmount);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", goodsInfo=").append(goodsInfo);
        sb.append(", failMsg=").append(failMsg);
        sb.append("]");
        return sb.toString();
    }
}