package com.wsbo.sharding.service;

import com.wsbo.sharding.dal.model.Trade;

public interface TradeService {
    Trade readFromReadDataSource(int id);
    void insert(Long tradeId);

}
