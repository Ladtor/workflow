package com.ladtor.workflow.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.service.executor.Executor;
import com.ladtor.workflow.core.service.executor.ExecutorFactory;
import com.ladtor.workflow.core.service.wrapper.WorkFlowWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private Executor executor;

    @Autowired
    private WorkFlowWrapper workFlowWrapper;

    @PostMapping(value = "/execute/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void execute(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion, @PathVariable String nodeId, @RequestBody JSONObject params) {
        ExecuteInfo executeInfo = ExecutorFactory.getExecuteInfo(new FourTuple(serialNo, version, runVersion, nodeId));
        executeInfo.setParams(params);
        executor.execute(executeInfo, false);
    }

    @PostMapping(value = "/success/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void success(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion, @PathVariable String nodeId, @RequestBody JSONObject result) {
        FourTuple fourTuple = new FourTuple(serialNo, version, runVersion, nodeId);
        Node node = workFlowWrapper.getNode(fourTuple);
        executor.success(ExecuteResult.builder()
                .nodeType(node.getNodeType())
                .fourTuple(fourTuple)
                .result(result)
                .build());
    }

    @PostMapping(value = "/fail/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void fail(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion, @PathVariable String nodeId, @RequestBody JSONObject result) {
        FourTuple fourTuple = new FourTuple(serialNo, version, runVersion, nodeId);
        Node node = workFlowWrapper.getNode(fourTuple);
        executor.fail(ExecuteResult.builder()
                .nodeType(node.getNodeType())
                .fourTuple(fourTuple)
                .result(result)
                .build());
    }

}
