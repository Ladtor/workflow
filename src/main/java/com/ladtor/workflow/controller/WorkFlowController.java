package com.ladtor.workflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ladtor.workflow.bo.domain.ExecuteLog;
import com.ladtor.workflow.bo.domain.WorkFlow;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.bo.execute.TwoTuple;
import com.ladtor.workflow.bo.req.GraphReq;
import com.ladtor.workflow.bo.req.WorkFlowReq;
import com.ladtor.workflow.bo.resp.WorkFlowResp;
import com.ladtor.workflow.exception.ClientException;
import com.ladtor.workflow.service.EdgeLogService;
import com.ladtor.workflow.service.ExecuteLogService;
import com.ladtor.workflow.service.NodeLogService;
import com.ladtor.workflow.service.WorkFlowService;
import com.ladtor.workflow.service.executor.Executor;
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
    private WorkFlowService workFlowService;
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
        String serialNo = workFlowService.save(workFlowReq.getName(), workFlowReq.getGraph());
        return serialNo;
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
        WorkFlowResp workFlowResp = workFlowService.get(serialNo, version);
        workFlowResp.setEdgeLogList(edgeLogService.list(threeTuple));
        workFlowResp.setNodeLogList(nodeLogService.list(threeTuple));
        workFlowResp.setRunVersion(runVersion);
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}/{version}",method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo, @PathVariable Integer version) {
        WorkFlowResp workFlowResp = workFlowService.get(serialNo, version);
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}", method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo) {
        WorkFlowResp workFlowResp = workFlowService.get(serialNo, null);
        if (workFlowResp == null) {
            throw new ClientException("该资源不存在或已被删除");
        }
        return workFlowResp;
    }

    @RequestMapping(value = "/{serialNo}/{version}", method = RequestMethod.PUT)
    public WorkFlowResp update(@PathVariable String serialNo, @PathVariable Integer version, @RequestBody GraphReq graph) {
        return workFlowService.update(serialNo, version, graph);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<WorkFlow> list() {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updated_at");
        return workFlowService.list(wrapper);
    }

    @RequestMapping(value = "/search/{keywords}", method = RequestMethod.GET)
    public List<WorkFlow> list(@PathVariable String keywords) {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.and(workFlowQueryWrapper -> workFlowQueryWrapper
                .likeRight("serial_no", keywords)
                .or()
                .likeRight("name", keywords)
        );
        wrapper.orderByDesc("updated_at").last(" limit 10 ");
        return workFlowService.list(wrapper);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer id) {
        return workFlowService.delete(id);
    }
}
