package com.ladtor.workflow.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.api.req.GraphReq;
import com.ladtor.workflow.api.req.WorkFlowReq;
import com.ladtor.workflow.api.resp.WorkFlowResp;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.common.bo.TwoTuple;
import com.ladtor.workflow.core.exception.ClientException;
import com.ladtor.workflow.core.service.executor.Executor;
import com.ladtor.workflow.core.service.wrapper.WorkFlowWrapper;
import com.ladtor.workflow.dao.*;
import com.ladtor.workflow.dao.domain.ExecuteLog;
import com.ladtor.workflow.dao.domain.Graph;
import com.ladtor.workflow.dao.domain.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/14 22:07
 */
@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    @Autowired
    private WorkFlowWrapper workFlowWrapper;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private GraphService graphService;
    @Autowired
    private ExecuteLogService executeLogService;
    @Autowired
    private Executor executor;
    @Autowired
    private EdgeLogService edgeLogService;
    @Autowired
    private NodeLogService nodeLogService;

    @RequestMapping(method = RequestMethod.POST)
    public String save(@RequestBody WorkFlowReq workFlowReq) {
        return workFlowWrapper.save(workFlowReq.getName(), workFlowReq.getGraph().getNodes(), workFlowReq.getGraph().getEdges());
    }

    @RequestMapping(value = "/execute/{serialNo}", method = RequestMethod.POST)
    public String execute(@PathVariable String serialNo, @RequestBody JSONObject params) {
        executor.execute(serialNo, params);
        return serialNo;
    }

    @RequestMapping(value = "/executeLog/{serialNo}", method = RequestMethod.GET)
    public List<ExecuteLog> executeLogs(@PathVariable String serialNo) {
        return executeLogService.listBySerialNo(serialNo);
    }

    @RequestMapping(value = "/{serialNo}/{version}/{runVersion}", method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo, @PathVariable Integer version, @PathVariable Integer runVersion) {
        TwoTuple twoTuple = new TwoTuple(serialNo, version);
        ThreeTuple threeTuple = new ThreeTuple(twoTuple, runVersion);
        WorkFlowResp workFlowResp = this.getWorkFlowResp(serialNo, version);
        workFlowResp.setEdgeLogList(edgeLogService.list(threeTuple));
        workFlowResp.setNodeLogList(nodeLogService.list(threeTuple));
        workFlowResp.setRunVersion(runVersion);
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}/{version}",method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo, @PathVariable Integer version) {
        WorkFlowResp workFlowResp = this.getWorkFlowResp(serialNo, version);
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}", method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo) {
        WorkFlowResp workFlowResp = this.getWorkFlowResp(serialNo, null);
        if (workFlowResp == null) {
            throw new ClientException("该资源不存在或已被删除");
        }
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}/{version}", method = RequestMethod.PUT)
    public WorkFlowResp update(@PathVariable String serialNo, @PathVariable Integer version, @RequestBody GraphReq graph) {
        version = workFlowWrapper.update(serialNo, version, graph.getNodes(), graph.getEdges());
        return this.getWorkFlowResp(serialNo, version);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<WorkFlow> list() {
        return workFlowService.list();
    }

    @RequestMapping(value = "/search/{keywords}", method = RequestMethod.GET)
    public List<WorkFlow> list(@PathVariable String keywords) {
        return workFlowService.searchBySerialNoOrName(keywords);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer id) {
        return workFlowService.delete(id);
    }

    public WorkFlowResp getWorkFlowResp(String serialNo, Integer version) {
        WorkFlow workFlow = workFlowService.get(serialNo);
        if (workFlow == null) {
            return null;
        }
        if (version == null) {
            version = workFlow.getVersion();
        }
        Graph graph = graphService.get(serialNo, version);
        return WorkFlowResp.builder()
                .id(workFlow.getId())
                .serialNo(serialNo)
                .name(workFlow.getName())
                .version(workFlow.getVersion())
                .runVersion(workFlow.getRunVersion())
                .graph(graph)
                .build();
    }
}
