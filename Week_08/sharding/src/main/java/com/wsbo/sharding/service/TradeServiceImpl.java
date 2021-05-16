package com.wsbo.sharding.service;

import com.wsbo.sharding.dal.mapper.TradeMapper;
import com.wsbo.sharding.dal.model.Trade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class TradeServiceImpl implements TradeService {
    @Resource
    private TradeMapper tradeMapper;
    private final Random random = new Random();

    @Override
    public Trade readFromReadDataSource(int id) {
        return tradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert(Long tradeId) {
        tradeMapper.insertSelective(Trade.builder()
                .tradeId(tradeId)
                .uid((long) random.nextInt(16*1000))
                .build());
    }
}
