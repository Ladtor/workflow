package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.dao.WorkFlowService;
import com.ladtor.workflow.dao.domain.WorkFlow;
import com.ladtor.workflow.dao.impl.mapper.WorkFlowMapper;

import java.util.List;

public class WorkFlowServiceImpl extends ServiceImpl<WorkFlowMapper, WorkFlow> implements WorkFlowService {
    @Override
    public WorkFlow get(String serialNo) {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo);
        return super.getOne(wrapper);
    }

    @Override
    public boolean update(WorkFlow workFlow) {
        return super.updateById(workFlow);
    }

    @Override
    public boolean delete(Integer id) {
        return super.removeById(id);
    }

    @Override
    public List<WorkFlow> list() {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updated_at");
        return super.list(wrapper);
    }

    @Override
    public List<WorkFlow> searchBySerialNoOrName(String keywords) {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.and(workFlowQueryWrapper -> workFlowQueryWrapper
                .likeRight("serial_no", keywords)
                .or()
                .likeRight("name", keywords)
        );
        wrapper.orderByDesc("updated_at").last(" limit 10 ");
        return super.list(wrapper);
    }
    //    @Transactional
//    public String save(String name, Graph graph) {
//        String serialNo = UUID.randomUUID().toString();
//        int version = 1;
//        this.save(WorkFlow.builder()
//                .name(name)
//                .serialNo(serialNo)
//                .version(version)
//                .runVersion(0)
//                .build());
//
//        graphService.save(serialNo, version, graph);
//        return serialNo;
//    }
//
//    public WorkFlow get(String serialNo, Integer version) {
//        WorkFlow workFlow = get(serialNo);
//        if (workFlow == null) {
//            return null;
//        }
//        if (version == null) {
//            version = workFlow.getVersion();
//        }
//        GraphReq graphReq = graphService.getReq(workFlow.getSerialNo(), version);
//        return WorkFlowResp.builder()
//                .id(workFlow.getId())
//                .serialNo(serialNo)
//                .name(workFlow.getName())
//                .version(workFlow.getVersion())
//                .runVersion(workFlow.getRunVersion())
//                .graph(graphReq)
//                .build();
//    }
//
//    @Cacheable(key = "#serialNo")
//    public WorkFlow get(String serialNo) {
//        QueryWrapper<WorkFlow> workFlowWrapper = new QueryWrapper<>();
//        workFlowWrapper.eq("serial_no", serialNo);
//        return this.getOne(workFlowWrapper);
//    }
//
//    @CacheEvict(key = "#workFlow.serialNo", beforeInvocation = true)
//    public void update(WorkFlow workFlow) {
//        this.updateById(workFlow);
//    }
//
//    public boolean delete(Integer id) {
//        return super.removeById(id);
//    }

//    @Transactional
//    public WorkFlow update(String serialNo, Integer version, Graph graph) {
//        WorkFlow workFlow = get(serialNo);
//        if (workFlow.getVersion().equals(version) && !workFlow.getHasBeenRun()) {
//            graphService.update(serialNo, version, graph);
//        } else {
//            version = workFlow.getVersion() + 1;
//            workFlow.setVersion(version);
//            workFlow.setHasBeenRun(false);
//            this.update(workFlow);
//            graphService.save(serialNo, version, graph);
//        }
//        return get(serialNo, version);
//    }

//    public Node getNode(FourTuple fourTuple) {
//        WorkFlowBo workFlow = getWorkFlow(fourTuple.getSerialNo(), fourTuple.getVersion());
//        Node node = workFlow.getGraph().getNode(fourTuple.getNodeId());
//        return node;
//    }
//
//    @Transactional
//    public Integer createRunVersion(String serialNo, Integer version, JSONObject params) {
//        WorkFlow workFlow = this.get(serialNo);
//        workFlow.setRunVersion(workFlow.getRunVersion() + 1);
//        workFlow.setHasBeenRun(true);
//        workFlow.setUpdatedAt(new Date());
//        this.update(workFlow);
//        executeLogService.save(new ThreeTuple(serialNo, version, workFlow.getRunVersion()), StatusEnum.RUNNING, params, null);
//        return workFlow.getRunVersion();
//    }
//
}
