package com.ladtor.workflow.converter;

import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.req.WorkFlowReq;

/**
 * @author liudongrong
 * @date 2019/1/14 22:29
 */
public class WorkFlowConverter implements Converter<WorkFlowReq, WorkFlowBo> {
    @Override
    public WorkFlowBo convertTo(WorkFlowReq obj) {
        return WorkFlowBo.builder()
                .graph(obj.getGraph())
                .name(obj.getName())
                .build();
    }
}
