package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.NodeLogService;
import com.ladtor.workflow.dao.domain.NodeLog;
import com.ladtor.workflow.dao.impl.mapper.NodeLogMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public class NodeLogServiceImpl extends ServiceImpl<NodeLogMapper, NodeLog> implements NodeLogService {

    @Cacheable(cacheNames = "nodeLogList", key = "#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
    public List<NodeLog> list(ThreeTuple threeTuple) {
        QueryWrapper<NodeLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", threeTuple.getSerialNo())
                .eq("version", threeTuple.getVersion())
                .eq("run_version", threeTuple.getRunVersion());
        return this.list(wrapper);
    }

    @Cacheable(cacheNames = "nodeLog", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion + #fourTuple.nodeId")
    public NodeLog get(FourTuple fourTuple) {
        QueryWrapper<NodeLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", fourTuple.getSerialNo())
                .eq("version", fourTuple.getVersion())
                .eq("run_version", fourTuple.getRunVersion())
                .eq("node_id", fourTuple.getNodeId());
        return getOne(wrapper);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "nodeLog", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion + #nodeLog.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion")
            }
    )
    public boolean saveOrUpdate(NodeLog nodeLog){
        return baseMapper.saveOrUpdate(nodeLog);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "nodeLog", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion + #nodeLog.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion")
            }
    )
    @Override
    public boolean update(NodeLog nodeLog) {
        return super.updateById(nodeLog);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "nodeLog", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion + #nodeLog.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#nodeLog.serialNo + #nodeLog.version + #nodeLog.runVersion")
            }
    )
    @Override
    public boolean save(NodeLog nodeLog) {
        return super.save(nodeLog);
    }
}
