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
    public void saveOrUpdate(FourTuple fourTuple, StatusEnum status, JSONObject params, JSONObject result) {
        QueryWrapper<NodeLog> wrapper = new QueryWrapper<>();
        String serialNo = fourTuple.getSerialNo();
        Integer version = fourTuple.getVersion();
        Integer runVersion = fourTuple.getRunVersion();
        String nodeId = fourTuple.getNodeId();
        wrapper.eq("serial_no", serialNo)
                .eq("version", version)
                .eq("run_version", runVersion)
                .eq("node_id", nodeId);
        NodeLog nodeLog = this.getOne(wrapper);
        if (nodeLog == null) {
            nodeLog = NodeLog.builder()
                    .serialNo(serialNo)
                    .version(version)
                    .runVersion(runVersion)
                    .nodeId(nodeId)
                    .status(status.toString())
                    .build();
            if (result != null) {
                nodeLog.setResult(result.toJSONString());
            }
            if (params != null) {
                nodeLog.setParams(params.toJSONString());
            }
            save(nodeLog);
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
        QueryWrapper<NodeLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", fourTuple.getSerialNo())
                .eq("version", fourTuple.getVersion())
                .eq("run_version", fourTuple.getRunVersion())
                .eq("node_id", fourTuple.getNodeId());
        NodeLog nodeLog = this.getOne(wrapper);
        nodeLog.setStatus(status.toString());
        nodeLog.setUpdatedAt(new Date());
        this.updateById(nodeLog);
    }
}
