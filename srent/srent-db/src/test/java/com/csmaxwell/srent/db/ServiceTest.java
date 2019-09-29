package com.csmaxwell.srent.db;

import com.csmaxwell.srent.db.domain.SrentGoods;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private SrentGoodsService srentGoodsService;

    @Test
    public void test() {
        SrentGoods goods = new SrentGoods();

        List<SrentGoods> srentGoods = srentGoodsService.queryByAll(0, 10);

        for (int i = 0; i < srentGoods.size(); i++) {
            System.out.println(srentGoods.get(i) + "");
        }
    }
}
