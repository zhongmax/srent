package com.csmaxwell.srent.admin.job;

import com.csmaxwell.srent.db.domain.SrentOrder;
import com.csmaxwell.srent.db.service.SrentOrderGoodsService;
import com.csmaxwell.srent.db.service.SrentOrderService;
import com.mysql.jdbc.log.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by maxwell on 2019/8/23
 */
@Component
public class OrderJob {
//    private final Log logger = LogFactory.getLogger(OrderJob.class);

    @Autowired
    private SrentOrderGoodsService orderGoodsService;
    @Autowired
    private SrentOrderService orderService;


}
