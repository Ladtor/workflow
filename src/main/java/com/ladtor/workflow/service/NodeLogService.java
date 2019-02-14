package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.domain.NodeLog;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.mapper.NodeLogMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 15:55
 */
@Service
@CacheConfig
public class NodeLogService extends ServiceImpl<NodeLogMapper, NodeLog> {

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
                    @CacheEvict(cacheNames = "nodeLog", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion + #fourTuple.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion")
            }
    )
    @Transactional
    public void saveOrUpdate(FourTuple fourTuple, StatusEnum status, JSONObject params, JSONObject result) {
        NodeLog nodeLog = this.get(fourTuple);
        if (nodeLog == null) {
            nodeLog = buildNodeLog(fourTuple, status, params, result);
            this.save(nodeLog);
        } else {
            nodeLog.setStatus(status.toString());
            nodeLog.setUpdatedAt(new Date());
            if (result != null) {
                nodeLog.setResult(result.toJSONString());
            }
            if (params != null) {
                nodeLog.setParams(params.toJSONString());
            }
            this.updateById(nodeLog);
        }
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "nodeLog", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion + #fourTuple.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion")
            }
    )
    public void updateStatus(FourTuple fourTuple, StatusEnum status) {
        NodeLog nodeLog = this.get(fourTuple);
        nodeLog.setStatus(status.toString());
        nodeLog.setUpdatedAt(new Date());
        this.updateById(nodeLog);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "nodeLog", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion + #fourTuple.nodeId"),
                    @CacheEvict(cacheNames = "nodeLogList", key = "#fourTuple.serialNo + #fourTuple.version + #fourTuple.runVersion")
            }
    )
    public boolean setPending(FourTuple fourTuple) {
        synchronized (fourTuple.getSerialNo().intern()) {
            NodeLog nodeLog = this.get(fourTuple);
            if (nodeLog == null) {
                nodeLog = buildNodeLog(fourTuple, StatusEnum.PENDING, null, null);
                this.save(nodeLog);
                return true;
            }
            if (StatusEnum.BLOCK.toString().equals(nodeLog.getStatus())) {
                updateStatus(fourTuple, StatusEnum.PENDING);
                return true;
            }
            return false;
        }
    }

    private NodeLog buildNodeLog(FourTuple fourTuple, StatusEnum statusEnum, JSONObject params, JSONObject result) {
        NodeLog nodeLog = NodeLog.builder()
                .serialNo(fourTuple.getSerialNo())
                .version(fourTuple.getVersion())
                .runVersion(fourTuple.getRunVersion())
                .nodeId(fourTuple.getNodeId())
                .status(statusEnum.toString())
                .build();
        if (params != null) {
            nodeLog.setParams(params.toJSONString());
        }
        if (result != null) {
            nodeLog.setResult(result.toJSONString());
        }
        return nodeLog;
    }
}
