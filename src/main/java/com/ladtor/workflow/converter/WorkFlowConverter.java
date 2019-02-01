package com.ladtor.workflow.converter;

import com.alibaba.fastjson.JSON;
import com.ladtor.workflow.bo.GraphBo;
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
                .graph(JSON.parseObject(JSON.toJSONString(obj.getGraph()), GraphBo.class))
                .name(obj.getName())
                .build();
    }
}
