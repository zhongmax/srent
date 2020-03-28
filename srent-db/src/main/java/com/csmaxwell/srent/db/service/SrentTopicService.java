package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentTopicMapper;
import com.csmaxwell.srent.db.domain.SrentTopic;
import com.csmaxwell.srent.db.domain.SrentTopic.*;
import com.csmaxwell.srent.db.domain.SrentTopicExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentTopicService {
    @Resource
    private SrentTopicMapper topicMapper;
    private Column[] columns = new Column[]{Column.id, Column.title, Column.subtitle, Column.price, Column.picUrl, Column.readCount};

    public List<SrentTopic> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public List<SrentTopic> queryList(int offset, int limit, String sort, String order) {
        SrentTopicExample example = new SrentTopicExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleSelective(example, columns);
    }

    public int queryTotal() {
        SrentTopicExample example = new SrentTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int) topicMapper.countByExample(example);
    }

    public SrentTopic findById(Integer id) {
        SrentTopicExample example = new SrentTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<SrentTopic> queryRelatedList(Integer id, int offset, int limit) {
        SrentTopicExample example = new SrentTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<SrentTopic> topics = topicMapper.selectByExample(example);
        if (topics.size() == 0) {
            return queryList(offset, limit, "add_time", "desc");
        }
        SrentTopic topic = topics.get(0);

        example = new SrentTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<SrentTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if (relateds.size() != 0) {
            return relateds;
        }

        return queryList(offset, limit, "add_time", "desc");
    }

    public List<SrentTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        SrentTopicExample example = new SrentTopicExample();
        SrentTopicExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int updateById(SrentTopic topic) {
        topic.setUpdateTime(LocalDateTime.now());
        SrentTopicExample example = new SrentTopicExample();
        example.or().andIdEqualTo(topic.getId());
        return topicMapper.updateByExampleSelective(topic, example);
    }

    public void deleteById(Integer id) {
        topicMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentTopic topic) {
        topic.setAddTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.insertSelective(topic);
    }


}
