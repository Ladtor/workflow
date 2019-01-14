package com.ladtor.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.domain.Graph;
import com.ladtor.workflow.mapper.GraphMapper;
import com.ladtor.workflow.util.serializer.GraphSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/14 22:33
 */
@Service
public class GraphService extends ServiceImpl<GraphMapper, Graph> {

    @Autowired
    private GraphSerializer graphSerializer;

    public void save(GraphBo graphBo) {
        this.save(Graph.builder()
                .data(graphSerializer.serialize(graphBo))
                .serialNo(graphBo.getSerialNo())
                .version(graphBo.getVersion())
                .build());
    }

    public GraphBo get(String serialNo, Integer version) {
        QueryWrapper<Graph> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).eq("version", version);
        Graph graph = this.getOne(wrapper);
        return graphSerializer.deserialize(graph.getData());
    }
}
