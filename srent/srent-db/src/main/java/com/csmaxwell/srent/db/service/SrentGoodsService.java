package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentGoodsMapper;
import com.csmaxwell.srent.db.domain.SrentGoods;
import com.csmaxwell.srent.db.domain.SrentGoodsExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import com.csmaxwell.srent.db.domain.SrentGoods.Column;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SrentGoodsService {

        Column[] columns = new Column[]{Column.id, Column.name, Column.gallery, Column.rentPrice, Column.userId, Column.deposit};

    @Resource
    private SrentGoodsMapper goodsMapper;

    /**
     * 获取所有商品
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<SrentGoods> queryByAll(int offset, int limit) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类下的商品
     *
     * @param catList
     * @param offset
     * @param limit
     * @return
     */
    public List<SrentGoods> queryByType(List<Integer> catList, int offset, int limit) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类下的商品
     *
     * @param catId
     * @param offset
     * @param limit
     * @return
     */
    public List<SrentGoods> queryByType(Integer catId, int offset, int limit) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 搜索商品
     *
     * @param keywords
     * @param offset   分页页数
     * @param limit    分页大小
     * @param sort     排序方式
     * @param order    排序类型，升序、降序
     * @return
     */
    public List<SrentGoods> querySelective(Integer catId, String keywords, Integer offset, Integer limit, String sort, String order) {
        SrentGoodsExample example = new SrentGoodsExample();
        SrentGoodsExample.Criteria criteria1 = example.or();
        SrentGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }

        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    public List<SrentGoods> querySelective(String goodsSn, String name, Integer page, Integer limit, String sort, String order) {
        SrentGoodsExample example = new SrentGoodsExample();
        SrentGoodsExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return goodsMapper.selectByExampleWithBLOBs(example);

    }

    /**
     * 获取某个商品信息，包含完整信息
     *
     * @param id
     * @return
     */
    public SrentGoods findById(Integer id) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }

    /**
     * 通过用户ID，获取商品信息
     * @param userId
     * @return
     */
    public List<SrentGoods> findByUserId(Integer userId,Integer page, Integer limit) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(page, limit);

        return goodsMapper.selectByExample(example);
    }

    public List<SrentGoods> findByUserId(Integer userId) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");

        return goodsMapper.selectByExample(example);
    }



    /**
     * 获取某个商品信息，仅获取一部分内容
     * 
     * @param id
     * @return
     */
    public SrentGoods findByIdVO(Integer id) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleSelective(example, columns);
    }

    /**
     * 获取商品总数
     * 
     * @return
     */
    public Integer count() {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andDeletedEqualTo(false);
        return  (int) goodsMapper.countByExample(example);
    }

    public int updateById(SrentGoods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }



    public void rentById(Integer id) {
        goodsMapper.logicalRentedByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        goodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentGoods goods) {
        goods.setAddTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.insertSelective(goods);
    }


    public List<Integer> getCatIds(String keywords) {
        SrentGoodsExample example = new SrentGoodsExample();
        SrentGoodsExample.Criteria criteria1 = example.or();
        SrentGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<SrentGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (SrentGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example) != 0;
    }

    public boolean checkExistByGoodsId(Integer GoodsId) {
        SrentGoodsExample example = new SrentGoodsExample();
        example.or().andIdEqualTo(GoodsId).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example) != 0;
    }

}
