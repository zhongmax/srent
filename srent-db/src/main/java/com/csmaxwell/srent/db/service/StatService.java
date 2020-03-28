package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.StatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by maxwell on 2019/8/23
 */
@Service
public class StatService {
    @Resource
    private StatMapper statMapper;

    public List<Map> statUser() {
        return statMapper.statUser();
    }

    public List<Map> statOrder() {
        return statMapper.statOrder();
    }

    public List<Map> statGoods() {
        return statMapper.statGoods();
    }
}
