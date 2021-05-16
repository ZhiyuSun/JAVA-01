package com.wsbo.readwriteseparate1.service;

import com.wsbo.readwriteseparate1.annotation.ReadOnly;
import com.wsbo.readwriteseparate1.dal.mapper.TradeMapper;
import com.wsbo.readwriteseparate1.dal.model.Trade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReadServiceImpl implements ReadService{
    @Resource
    private TradeMapper tradeMapper;

    @Override
    public Trade readFromWriteDataSource(int id) {
        return tradeMapper.selectByPrimaryKey(id);
    }

    @Override
    @ReadOnly
    public Trade readFromReadDataSource(int id) {
        return tradeMapper.selectByPrimaryKey(id);
    }
}
