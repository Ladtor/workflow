package com.ladtor.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.domain.WorkFlow;
import com.ladtor.workflow.bo.resp.WorkFlowResp;
import com.ladtor.workflow.mapper.WorkFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author liudongrong
 * @date 2019/1/13 10:22
 */
@Service
public class WorkFlowService extends ServiceImpl<WorkFlowMapper, WorkFlow> {

    @Autowired
    private GraphService graphService;


    public WorkFlowBo getWorkFlow(String serialNo, Integer version) {
        WorkFlow workFlow = get(serialNo);
        GraphBo graphBo = graphService.get(serialNo, version);
        return WorkFlowBo.builder()
                .name(workFlow.getName())
                .graph(graphBo)
                .build();
    }

    @Transactional
    public String save(String name, GraphBo graph) {
        String serialNo = UUID.randomUUID().toString();
        int version = 1;
        this.save(WorkFlow.builder()
                .name(name)
                .serialNo(serialNo)
                .version(version)
                .runVersion(0)
                .build());

        graph.setSerialNo(serialNo);
        graph.setVersion(version);
        graphService.save(graph);
        return serialNo;
    }

    public WorkFlowResp get(String serialNo, Integer version) {
        WorkFlow workFlow = get(serialNo);
        if (version == null) {
            version = workFlow.getVersion();
        }
        GraphBo graphBo = graphService.get(workFlow.getSerialNo(), version);
        return WorkFlowResp.builder()
                .id(workFlow.getId())
                .serialNo(serialNo)
                .name(workFlow.getName())
                .version(workFlow.getVersion())
                .runVersion(workFlow.getRunVersion())
                .graph(graphBo)
                .build();
    }

    public WorkFlow get(String serialNo) {
        QueryWrapper<WorkFlow> workFlowWrapper = new QueryWrapper<>();
        workFlowWrapper.eq("serial_no", serialNo);
        return this.getOne(workFlowWrapper);
    }

    public void update(WorkFlow workFlow) {
        this.updateById(workFlow);
    }
}
