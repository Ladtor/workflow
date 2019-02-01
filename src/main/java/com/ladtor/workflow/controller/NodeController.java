package com.ladtor.workflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.service.WorkFlowService;
import com.ladtor.workflow.service.executor.Executor;
import com.ladtor.workflow.service.executor.ExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liudongrong
 * @date 2019/1/22 21:03
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private Executor executor;

    @Autowired
    private WorkFlowService workFlowService;

    @RequestMapping(value = "/execute/{serialNo}/{version}/{runVersion}/{nodeId}", method = RequestMethod.POST)
    public void execute(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion, @PathVariable String nodeId, @RequestBody JSONObject params) {
        ExecuteInfo executeInfo = ExecutorFactory.getExecuteInfo(new FourTuple(serialNo, version, runVersion, nodeId));
        executeInfo.setParams(params);
        executor.execute(executeInfo);
    }

    @RequestMapping(value = "/success/{serialNo}/{version}/{runVersion}/{nodeId}", method = RequestMethod.POST)
    public void success(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion, @PathVariable String nodeId, @RequestBody JSONObject result) {
        FourTuple fourTuple = new FourTuple(serialNo, version, runVersion, nodeId);
        Node node = workFlowService.getNode(fourTuple);
        executor.success(ExecuteResult.builder()
                .nodeType(node.getNodeType())
                .fourTuple(fourTuple)
                .result(result)
                .build());
    }
}
