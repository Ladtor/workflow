package com.ladtor.workflow.controller;

import com.ladtor.workflow.bo.req.WorkFlowReq;
import com.ladtor.workflow.bo.resp.WorkFlowResp;
import com.ladtor.workflow.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liudongrong
 * @date 2019/1/14 22:07
 */
@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @RequestMapping(method = RequestMethod.POST)
    public String save(WorkFlowReq workFlowReq){
        String serialNo = workFlowService.save(workFlowReq.getName(), workFlowReq.getGraph());
        return serialNo;
    }

    @RequestMapping(value = "/{serialNo}/{version}",method = RequestMethod.GET)
    public WorkFlowResp get(@PathVariable String serialNo, @PathVariable(required = false) Integer version){
        WorkFlowResp workFlowResp = workFlowService.get(serialNo, version);
        return workFlowResp;
    }

}
