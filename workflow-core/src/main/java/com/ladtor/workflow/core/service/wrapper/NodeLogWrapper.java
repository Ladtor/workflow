package com.ladtor.workflow.core.service.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.common.constant.StatusEnum;
import com.ladtor.workflow.dao.NodeLogService;
import com.ladtor.workflow.dao.domain.NodeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NodeLogWrapper {
    @Autowired
    private NodeLogService nodeLogService;

    public void updateStatus(FourTuple fourTuple, StatusEnum status) {
        NodeLog nodeLog = nodeLogService.get(fourTuple);
        nodeLog.setStatus(status.toString());
        nodeLog.setUpdatedAt(new Date());
        nodeLogService.update(nodeLog);
    }

    public void saveOrUpdate(FourTuple fourTuple, StatusEnum status, JSONObject params, JSONObject result) {
        NodeLog nodeLog = buildNodeLog(fourTuple, status, params, result);
        nodeLogService.saveOrUpdate(nodeLog);
    }

    public boolean setPending(FourTuple fourTuple) {
        synchronized (this){
            NodeLog nodeLog = nodeLogService.get(fourTuple);
            if (nodeLog == null) {
                nodeLog = buildNodeLog(fourTuple, StatusEnum.PENDING, null, null);
                nodeLogService.save(nodeLog);
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
