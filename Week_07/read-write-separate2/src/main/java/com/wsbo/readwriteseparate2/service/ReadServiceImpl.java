package com.wsbo.readwriteseparate2.service;

import com.wsbo.readwriteseparate2.dal.mapper.TradeMapper;
import com.wsbo.readwriteseparate2.dal.model.Trade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReadServiceImpl implements ReadService{
    @Resource
    private TradeMapper tradeMapper;

    @Override
    public Trade readFromReadDataSource(int id) {
        return tradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert() {
        tradeMapper.insertSelective(Trade.builder()
                .tradeId("test_trade_id")
                .uid("test_uid")
                .build());
    }
}
