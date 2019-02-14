package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.domain.WorkFlow;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.bo.req.GraphReq;
import com.ladtor.workflow.bo.resp.WorkFlowResp;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.mapper.WorkFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author liudongrong
 * @date 2019/1/13 10:22
 */
@Service
@CacheConfig(cacheNames = "workFlow")
public class WorkFlowService extends ServiceImpl<WorkFlowMapper, WorkFlow> {

    @Autowired
    private GraphService graphService;

    @Autowired
    private ExecuteLogService executeLogService;


    public WorkFlowBo getWorkFlow(String serialNo) {
        return getWorkFlow(serialNo, null);
    }

    public WorkFlowBo getWorkFlow(String serialNo, Integer version) {
        WorkFlow workFlow = get(serialNo);
        if (version == null) {
            version = workFlow.getVersion();
        }
        GraphBo graphBo = graphService.getBo(serialNo, version);
        return WorkFlowBo.builder()
                .serialNo(serialNo)
                .version(version)
                .name(workFlow.getName())
                .graph(graphBo)
                .build();
    }

    @Transactional
    public String save(String name, GraphReq graph) {
        String serialNo = UUID.randomUUID().toString();
        int version = 1;
        this.save(WorkFlow.builder()
                .name(name)
                .serialNo(serialNo)
                .version(version)
                .runVersion(0)
                .build());

        graphService.save(serialNo, version, graph);
        return serialNo;
    }

    public WorkFlowResp get(String serialNo, Integer version) {
        WorkFlow workFlow = get(serialNo);
        if (workFlow == null) {
            return null;
        }
        if (version == null) {
            version = workFlow.getVersion();
        }
        GraphReq graphReq = graphService.getReq(workFlow.getSerialNo(), version);
        return WorkFlowResp.builder()
                .id(workFlow.getId())
                .serialNo(serialNo)
                .name(workFlow.getName())
                .version(workFlow.getVersion())
                .runVersion(workFlow.getRunVersion())
                .graph(graphReq)
                .build();
    }

    @Cacheable(key = "#serialNo")
    public WorkFlow get(String serialNo) {
        QueryWrapper<WorkFlow> workFlowWrapper = new QueryWrapper<>();
        workFlowWrapper.eq("serial_no", serialNo);
        return this.getOne(workFlowWrapper);
    }

    @CacheEvict(key = "#workFlow.serialNo", beforeInvocation = true)
    public void update(WorkFlow workFlow) {
        this.updateById(workFlow);
    }

    @Transactional
    public WorkFlowResp update(String serialNo, Integer version, GraphReq graphReq) {
        WorkFlow workFlow = get(serialNo);
        if (workFlow.getVersion().equals(version) && !workFlow.getHasBeenRun()) {
            graphService.update(serialNo, version, graphReq);
        } else {
            version = workFlow.getVersion() + 1;
            workFlow.setVersion(version);
            workFlow.setHasBeenRun(false);
            this.update(workFlow);
            graphService.save(serialNo, version, graphReq);
        }
        return get(serialNo, version);
    }

    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    public Node getNode(FourTuple fourTuple) {
        WorkFlowBo workFlow = getWorkFlow(fourTuple.getSerialNo(), fourTuple.getVersion());
        Node node = workFlow.getGraph().getNode(fourTuple.getNodeId());
        return node;
    }

    @Transactional
    public Integer createRunVersion(String serialNo, Integer version, JSONObject params) {
        WorkFlow workFlow = this.get(serialNo);
        workFlow.setRunVersion(workFlow.getRunVersion() + 1);
        workFlow.setHasBeenRun(true);
        workFlow.setUpdatedAt(new Date());
        this.update(workFlow);
        executeLogService.save(new ThreeTuple(serialNo, version, workFlow.getRunVersion()), StatusEnum.RUNNING, params, null);
        return workFlow.getRunVersion();
    }
}
