package com.wsbo.sharding.dal.mapper;

import com.wsbo.sharding.dal.model.Trade;
import com.wsbo.sharding.dal.model.TradeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TradeMapper {
    long countByExample(TradeExample example);

    int deleteByExample(TradeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Trade record);

    int insertSelective(Trade record);

    List<Trade> selectByExampleWithBLOBsWithRowbounds(TradeExample example, RowBounds rowBounds);

    List<Trade> selectByExampleWithBLOBs(TradeExample example);

    List<Trade> selectByExampleWithRowbounds(TradeExample example, RowBounds rowBounds);

    List<Trade> selectByExample(TradeExample example);

    Trade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Trade record, @Param("example") TradeExample example);

    int updateByExampleWithBLOBs(@Param("record") Trade record, @Param("example") TradeExample example);

    int updateByExample(@Param("record") Trade record, @Param("example") TradeExample example);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKeyWithBLOBs(Trade record);

    int updateByPrimaryKey(Trade record);
}