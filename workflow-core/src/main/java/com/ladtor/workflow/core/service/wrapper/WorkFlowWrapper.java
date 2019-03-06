package com.ladtor.workflow.core.service.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.common.constant.StatusEnum;
import com.ladtor.workflow.core.bo.GraphBo;
import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.core.bo.WorkFlowBo;
import com.ladtor.workflow.dao.WorkFlowService;
import com.ladtor.workflow.dao.domain.Graph;
import com.ladtor.workflow.dao.domain.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WorkFlowWrapper {
    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private GraphWrapper graphWrapper;

    @Autowired
    private ExecuteLogWrapper executeLogWrapper;

    public WorkFlowBo getWorkFlow(String serialNo, Integer version) {
        WorkFlow workFlow = workFlowService.get(serialNo);
        if (version == null) {
            version = workFlow.getVersion();
        }
        GraphBo graphBo = graphWrapper.getBo(serialNo, version);
        return WorkFlowBo.builder()
                .serialNo(serialNo)
                .version(version)
                .name(workFlow.getName())
                .graph(graphBo)
                .build();
    }

    public WorkFlowBo getWorkFlow(String serialNo) {
        return getWorkFlow(serialNo, null);
    }

    public Node getNode(FourTuple fourTuple) {
        WorkFlowBo workFlow = getWorkFlow(fourTuple.getSerialNo(), fourTuple.getVersion());
        Node node = workFlow.getGraph().getNode(fourTuple.getNodeId());
        return node;
    }

    @Transactional
    public Integer createRunVersion(String serialNo, Integer version, JSONObject params) {
        WorkFlow workFlow = workFlowService.get(serialNo);
        workFlow.setRunVersion(workFlow.getRunVersion() + 1);
        workFlow.setHasBeenRun(true);
        workFlow.setUpdatedAt(new Date());
        workFlowService.update(workFlow);
        executeLogWrapper.save(new ThreeTuple(serialNo, version, workFlow.getRunVersion()), StatusEnum.RUNNING, params, null);
        return workFlow.getRunVersion();
    }

    @Transactional
    public String save(String name, List<JSONObject> nodes, List<JSONObject> edges) {
        String serialNo = UUID.randomUUID().toString();
        int version = 1;
        workFlowService.save(WorkFlow.builder()
                .name(name)
                .serialNo(serialNo)
                .version(version)
                .runVersion(0)
                .build());

        Graph graph = buildGraph(serialNo, version, nodes, edges);
        graphWrapper.save(serialNo, version, graph);
        return serialNo;
    }

    @Transactional
    public Integer update(String serialNo, Integer version, List<JSONObject> nodes, List<JSONObject> edges) {
        Graph graph = buildGraph(serialNo, version, nodes, edges);
        synchronized (this) {
            WorkFlow workFlow = workFlowService.get(serialNo);
            if (workFlow.getVersion().equals(version) && !workFlow.getHasBeenRun()) {
                graphWrapper.update(serialNo, version, nodes, edges);
            } else {
                version = workFlow.getVersion() + 1;
                workFlow.setVersion(version);
                workFlow.setHasBeenRun(false);
                workFlowService.update(workFlow);
                graphWrapper.save(serialNo, version, graph);
            }
        }
        return version;
    }

    private Graph buildGraph(String serialNo, int version, List<JSONObject> nodes, List<JSONObject> edges) {
        return Graph.builder()
                .serialNo(serialNo)
                .version(version)
                .nodes(nodes)
                .edges(edges)
                .build();
    }
}
