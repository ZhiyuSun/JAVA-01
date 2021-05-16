package com.wsbo.batchinsert.inserter;

import com.wsbo.batchinsert.dal.mapper.TradeMapper;
import com.wsbo.batchinsert.dal.model.Trade;
import com.wsbo.batchinsert.util.ApplicationContextUtil;

import java.math.BigDecimal;
import java.util.Date;

public class SimpleInserter {
    public static final int ROWS = 10000;
    public static void insert() {
        long startTime = System.currentTimeMillis();
        final TradeMapper tradeMapper = ApplicationContextUtil.getBean(TradeMapper.class);
        final Trade trade = Trade.builder()
                .goodsInfo("[]")
                .payTime(new Date())
                .totalAmount(BigDecimal.ZERO)
                .receivedAmount(BigDecimal.ZERO)
                .tradeId("trade1")
                .uid("user1")
                .build();
        for (int i = 0; i < ROWS; i++) {
            tradeMapper.insertSelective(trade);
        }
        System.out.println("SimpleInserter costs " + (System.currentTimeMillis() - startTime) + "ms");
    }



}
