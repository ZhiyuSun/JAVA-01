package com.wsbo.readwriteseparate1.service;

import com.wsbo.readwriteseparate1.dal.model.Trade;

public interface ReadService {
    Trade readFromWriteDataSource(int id);
    Trade readFromReadDataSource(int id);

}
