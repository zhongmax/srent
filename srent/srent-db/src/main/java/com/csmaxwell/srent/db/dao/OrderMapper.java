package com.csmaxwell.srent.db.dao;

import com.csmaxwell.srent.db.domain.SrentOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime,
                                   @Param("order") SrentOrder order);
}
