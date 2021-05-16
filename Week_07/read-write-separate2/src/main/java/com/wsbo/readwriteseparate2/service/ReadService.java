package com.wsbo.readwriteseparate2.service;

import com.wsbo.readwriteseparate2.dal.model.Trade;

public interface ReadService {
    Trade readFromReadDataSource(int id);
    void insert();

}
