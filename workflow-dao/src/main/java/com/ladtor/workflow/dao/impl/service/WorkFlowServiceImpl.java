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
    public boolean delete(String serialNo) {
        QueryWrapper<WorkFlow> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo);
        return super.remove(wrapper);
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
}
